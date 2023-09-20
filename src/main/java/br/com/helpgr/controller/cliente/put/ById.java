package br.com.helpgr.controller.cliente.put;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.model.Cliente;
import br.com.helpgr.repositore.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ById {

    @Inject
    ClienteRepository clienteRepository;

    @PUT
    @Path("/{id}")
    @Tag(name = "Clientes", description = "Gerencie os clientes da sua aplicação.")
    @Operation(
            summary = "Atualizar Cliente por ID",
            description = "Atualize os detalhes de um cliente existente com base no seu ID."
    )
    @RequestBody(
            description = "Detalhes do cliente a serem atualizados.",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Atendente.class)
            )
    )
    @APIResponse(
            responseCode = "200",
            description = "Cliente atualizado com sucesso.",
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

    public Response byId(
            @PathParam("id") String id, // O ID do cliente a ser atualizado
            Cliente clienteAtualizado // O objeto com os dados atualizados
    ) {
        // Verifique se o cliente com o ID especificado existe
        Cliente clienteExistente = clienteRepository.findById(new ObjectId(id));

        if (clienteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente não encontrado com o ID: " + id)
                    .build();
        }

        // Campos que serão atualizados
        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setEmail(clienteAtualizado.getEmail());

        clienteRepository.update(clienteExistente);

        return Response.ok(clienteExistente).build();
    }

}
