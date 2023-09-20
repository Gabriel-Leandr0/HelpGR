package br.com.helpgr.controller.atendente.del;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.repositore.AtendenteRepository;
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

@Path("/atendente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ById {
    @Inject
    AtendenteRepository atendenteRepository;

    @DELETE
    @Path("/{id}")
    @Tag(name = "Atendentes", description = "Gerencie os atendentes da sua aplicação.")
    @Operation(
            summary = "Excluir Atendente por ID",
            description = "Exclua um atendente existente com base no seu ID."
    )
    @APIResponse(
            responseCode = "204",
            description = "Atendente excluído com sucesso."
    )
    @APIResponse(
            responseCode = "404",
            description = "Atendente não encontrado",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class)
            )
    )

    public Response byId(@PathParam("id") String id) {
        // Verifique se o atendente com o ID especificado existe
        Atendente atendenteExistente = atendenteRepository.findById(new ObjectId(id));

        if (atendenteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Atendente não encontrado com o ID: " + id)
                    .build();
        }

        // Execute a exclusão do atendente
        atendenteRepository.delete(atendenteExistente);

        return Response.noContent().build();
    }
}
