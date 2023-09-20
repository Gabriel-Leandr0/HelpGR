package br.com.helpgr.controller.cliente.del;

import br.com.helpgr.model.Cliente;
import br.com.helpgr.repository.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ById {

    @Inject
    ClienteRepository clienteRepository;

    @DELETE
    @Tag(name = "Clientes", description = "Gerencie os clientes da sua aplicação.")
    @Path("/{id}")
    @Operation(
            summary = "Excluir Cliente por ID",
            description = "Exclua um cliente existente com base no seu ID."
    )
    @APIResponse(
            responseCode = "204",
            description = "Cliente excluído com sucesso."
    )
    @APIResponse(
            responseCode = "404",
            description = "Cliente não encontrado",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class)
            )
    )

    public Response byId(@PathParam("id") String id) {
        // Verifique se o cliente com o ID especificado existe
        Cliente clienteExistente = clienteRepository.findById(new ObjectId(id));

        if (clienteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente não encontrado com o ID: " + id)
                    .build();
        }

        // Execute a exclusão do cliente
        clienteRepository.delete(clienteExistente);

        return Response.noContent().build();
    }

}
