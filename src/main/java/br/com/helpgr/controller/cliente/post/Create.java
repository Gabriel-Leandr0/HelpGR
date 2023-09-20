package br.com.helpgr.controller.cliente.post;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.model.Cliente;
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

@Path("/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Create {

    @Inject
    ClienteRepository clienteRepository;

    @POST
    @Tag(name = "Clientes", description = "Gerencie os clientes da sua aplicação.")
    @Operation(
            summary = "Criar Cliente",
            description = "Crie um novo cliente na aplicação."
    )
    @RequestBody(
            description = "Detalhes do novo cliente a ser criado.",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Atendente.class)
            )
    )
    @APIResponse(
            responseCode = "201",
            description = "Cliente criado com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Atendente.class)
            )
    )
    public Response create(Cliente cliente) throws URISyntaxException {
        clienteRepository.persist(cliente);
        return Response.created(new URI("/" + cliente.id)).build();
    }

}
