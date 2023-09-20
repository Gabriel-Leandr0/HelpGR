package br.com.helpgr.controller.chamado.put;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.model.Chamado;
import br.com.helpgr.repository.AtendenteRepository;
import br.com.helpgr.repository.ChamadoRepository;
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

import java.time.LocalDate;

@Path("/chamado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ById {

    @Inject
    ChamadoRepository chamadoRepository;
    @Inject
    AtendenteRepository atendenteRepository;

    @PUT
    @Path("/{id}")
    @Tag(name = "Chamados", description = "Gerencie os chamados da sua aplicação.")
    @Operation(
            summary = "Atualizar Chamado por ID",
            description = "Atualize um chamado existente com base no seu ID."
    )
    @RequestBody(
            description = "Informações atualizadas do chamado.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Chamado.class)
            )
    )
    @APIResponse(
            responseCode = "200",
            description = "Chamado atualizado com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Chamado.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Chamado não encontrado",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Atendente não encontrado",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class)
            )
    )

    public Response byId(
            @PathParam("id") String id, // O ID do chamado a ser atualizado
            Chamado chamadoAtualizado // O objeto com os dados atualizados
    ) {
        // Verifique se o chamado com o ID especificado existe
        Chamado chamadoExistente = chamadoRepository.findById(new ObjectId(id));
        if (chamadoExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Chamado não encontrado com o ID: " + id)
                    .build();
        }

        String atendenteEmail = chamadoAtualizado.getAtendenteEmail();
        Atendente atendenteExistente = atendenteRepository.findByEmail(atendenteEmail);

        // Verifica se o email do atendente existe
        if (atendenteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Atendente não encontrado com o E-mail: " + atendenteEmail)
                    .build();
        }

        // Verifique se o status foi alterado para "encerrado"
        String novoStatus = chamadoAtualizado.getStatus();
        if ("encerrado".equalsIgnoreCase(novoStatus) && !"encerrado".equalsIgnoreCase(chamadoExistente.getStatus())) {
            // O status foi alterado para "encerrado", portanto, atualize a dataEncerrado com a data atual
            chamadoExistente.setDataEncerrado(LocalDate.now());
        }

        // Atualize os campos relevantes do chamado existente com os dados do chamado atualizado
        chamadoExistente.setSolucao(chamadoAtualizado.getSolucao());
        chamadoExistente.setAtendenteEmail(chamadoAtualizado.getAtendenteEmail());
        chamadoExistente.setStatus(novoStatus);

        // Salve o chamado atualizado no banco de dados
        chamadoRepository.update(chamadoExistente);

        return Response.ok(chamadoExistente).build();
    }

}
