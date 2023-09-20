package br.com.helpgr.controller.chamado.del;

import br.com.helpgr.model.Chamado;
import br.com.helpgr.repositore.ChamadoRepository;
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

@Path("/chamado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ById {

    @Inject
    ChamadoRepository chamadoRepository;

    @DELETE
    @Path("/{id}")
    @Tag(name = "Chamados", description = "Gerencie os chamados da sua aplicação.")
    @Operation(
            summary = "Excluir Chamado por ID",
            description = "Exclua um chamado existente com base no seu ID."
    )
    @APIResponse(
            responseCode = "204",
            description = "Chamado excluído com sucesso."
    )
    @APIResponse(
            responseCode = "404",
            description = "Chamado não encontrado",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class)
            )
    )

    public Response byId(@PathParam("id") String id) {

        // Verifica se o chamado com o ID especificado existe
        Chamado chamadoExistente = chamadoRepository.findById(new ObjectId(id));

        if (chamadoExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Chamado não encontrado com o ID: " + id)
                    .build();
        }

        // Executa a exclusão do chamado
        chamadoRepository.delete(chamadoExistente);

        return Response.noContent().build();
    }
}
