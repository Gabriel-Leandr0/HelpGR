package br.com.helpgr.controller.chamado.get;

import br.com.helpgr.model.Chamado;
import br.com.helpgr.repository.ChamadoRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/chamado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ByStatus {
    @Inject
    ChamadoRepository chamadoRepository;

    @GET
    @Path("/status/{status}")
    @Tag(name = "Chamados", description = "Gerencie os chamados da sua aplicação.")
    @Operation(
            summary = "Buscar Chamados por Status",
            description = "Recupere uma lista de chamados com base no status especificado."
    )
    @Parameter(
            name = "status",
            in = ParameterIn.PATH,
            required = true,
            description = "Status do chamado (por exemplo, 'aberto', 'fechado', etc.)."
    )
    @APIResponse(
            responseCode = "200",
            description = "Chamados encontrados com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Chamado.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Nenhum chamado encontrado",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class)
            )
    )
    public Response byStatus(@PathParam("status") String status) {
        List<Chamado> chamados = chamadoRepository.list("status", status);

        if (chamados.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum chamado encontrado com o status: " + status)
                    .build();
        }

        return Response.ok(chamados).build();
    }
}
