package br.com.helpgr.controller.chamado.post;

import br.com.helpgr.model.Chamado;
import br.com.helpgr.model.Cliente;
import br.com.helpgr.repositore.ChamadoRepository;
import br.com.helpgr.repositore.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

@Path("/chamado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class Create {
    @Inject
    ChamadoRepository chamadoRepository;
    @Inject
    ClienteRepository clienteRepository;

    @POST
    public Response create(Chamado chamado) throws URISyntaxException {
        // Verifique se o cliente associado existe pelo email
        if (chamado.clienteEmail != null) {
            Cliente clienteExistente = clienteRepository.findByEmail(chamado.clienteEmail);
            if (clienteExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Cliente não encontrado com o E-mail: " + chamado.clienteEmail)
                        .build();
            }
        }

        // Defina a data de abertura como a data e hora atuais
        chamado.setDataAbertura(LocalDate.now());

        chamadoRepository.persist(chamado);
        return Response.created(new URI("/" + chamado.id)).build();
    }
}
