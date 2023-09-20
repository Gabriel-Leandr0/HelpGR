package br.com.helpgr.controller.chamado.post;

import br.com.helpgr.model.Chamado;
import br.com.helpgr.model.Cliente;
import br.com.helpgr.repository.ChamadoRepository;
import br.com.helpgr.repository.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

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
    @Tag(name = "Chamados", description = "Gerencie os chamados da sua aplicação.")
    @Operation(
            summary = "Criar Chamado",
            description = "Crie um novo chamado com base nas informações fornecidas."
    )
    @RequestBody(
            description = "Informações do chamado a ser criado.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Chamado.class)
            )
    )
    @APIResponse(
            responseCode = "201",
            description = "Chamado criado com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Chamado.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Cliente não encontrado",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class)
            )
    )

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
        return Response.created(new URI("/" + chamado.id)).entity("Chamado criado!").build();
    }
}
