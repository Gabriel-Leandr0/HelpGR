package br.com.helpgr.controller.cliente.get;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.model.Cliente;
import br.com.helpgr.repositore.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ByEmail {

    @Inject
    ClienteRepository clienteRepository;

    @GET
    @Path("/{email}")
    @Tag(name = "Clientes", description = "Gerencie os clientes da sua aplicação.")
    @Operation(
            summary = "Buscar Cliente por E-mail",
            description = "Recupere um cliente com base no seu endereço de e-mail."
    )
    @APIResponse(
            responseCode = "200",
            description = "Cliente encontrado com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Atendente.class)
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

    public Response byEmail(@PathParam("email") String email) {
        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente não encontrado com o E-mail: " + email)
                    .build();
        }

        return Response.ok(cliente).build();
    }

}
