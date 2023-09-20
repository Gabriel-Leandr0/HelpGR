package br.com.helpgr.controller.atendente.put;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.repository.AtendenteRepository;
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

@Path("/atendente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ById {

    @Inject
    AtendenteRepository atendenteRepository;

    @PUT
    @Path("/{id}")
    @Tag(name = "Atendentes", description = "Gerencie os atendentes da sua aplicação.")
    @Operation(
            summary = "Atualizar Atendente por ID",
            description = "Atualize um atendente existente com base no seu ID."
    )
    @RequestBody(
            description = "Informações atualizadas do atendente.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Atendente.class)
            )
    )
    @APIResponse(
            responseCode = "200",
            description = "Atendente atualizado com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Atendente.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Atendente não encontrado",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class)
            )
    )
    public Response byId(
            @PathParam("id") String id, // O ID do atendente a ser atualizado
            Atendente atendenteAtualizado // O objeto com os dados atualizados
    ) {
        // Verifique se o atendente com o ID especificado existe
        Atendente atendenteExistente = atendenteRepository.findById(new ObjectId(id));

        if (atendenteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Atendente não encontrado com o ID: " + id)
                    .build();
        }

        // Campos que serão atualizados
        atendenteExistente.setNome(atendenteAtualizado.getNome());
        atendenteExistente.setEmail(atendenteAtualizado.getEmail());

        atendenteRepository.update(atendenteExistente);

        return Response.ok(atendenteExistente).build();
    }

}
