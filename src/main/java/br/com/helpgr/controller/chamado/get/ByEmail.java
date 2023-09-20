package br.com.helpgr.controller.chamado.get;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.model.Chamado;
import br.com.helpgr.model.Cliente;
import br.com.helpgr.repositore.AtendenteRepository;
import br.com.helpgr.repositore.ChamadoRepository;
import br.com.helpgr.repositore.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/chamado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ByEmail {

    @Inject
    ChamadoRepository chamadoRepository;
    @Inject
    ClienteRepository clienteRepository;
    @Inject
    AtendenteRepository atendenteRepository;

    @GET
    @Path("/{email}")
    public Response byEmail(@PathParam("email") String email) {

        Cliente cliente = clienteRepository.findByEmail(email);
        Atendente atendente = atendenteRepository.findByEmail(email);

        List<Chamado> chamados = new ArrayList<>();

        if (cliente != null) {
            // Se o email pertence a um cliente, procuramos os chamados associados ao cliente
            chamados = chamadoRepository.find("clienteEmail", email).list();
        } else if (atendente != null) {
            // Se o email pertence a um atendente, procuramos os chamados associados ao atendente
            chamados = chamadoRepository.find("atendenteEmail", email).list();
        }

        if (chamados.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum chamado encontrado para o E-mail: " + email)
                    .build();
        }

        return Response.ok(chamados).build();
    }
}
