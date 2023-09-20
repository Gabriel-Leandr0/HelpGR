package br.com.helpgr.controller.chamado.get;

import br.com.helpgr.model.Chamado;
import br.com.helpgr.repositore.ChamadoRepository;
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

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Path("/chamado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ByOpeningDate {
    @Inject
    ChamadoRepository chamadoRepository;

    @GET
    @Path("/dataAbert/{data}")
    @Tag(name = "Chamados", description = "Gerencie os chamados da sua aplicação.")
    @Operation(
            summary = "Buscar Chamados por Data de Abertura",
            description = "Recupere uma lista de chamados com base na data de abertura especificada."
    )
    @Parameter(
            name = "data",
            in = ParameterIn.PATH,
            required = true,
            description = "Data de abertura no formato ISO (AAAA-MM-DD)."
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
    @APIResponse(
            responseCode = "400",
            description = "Formato de data inválido",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class)
            )
    )
    public Response byOpeningDate(@PathParam("data") String data) {
        try {
            LocalDate dataAbertura = LocalDate.parse(data);

            List<Chamado> chamados = chamadoRepository.list("dataAbertura", dataAbertura);

            if (chamados.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Nenhum chamado encontrado com a data de abertura: " + data)
                        .build();
            }

            return Response.ok(chamados).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Formato de data inválido. Use o formato ISO (AAAA-MM-DD).")
                    .build();
        }
    }
}
