package br.com.helpgr.controller.chamado.get;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.repositore.ChamadoRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/chamado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class All {
    @Inject
    ChamadoRepository chamadoRepository;
    @GET
    @Tag(name = "Chamados", description = "Gerencie os chamados da sua aplicação.")
    @Operation(
            summary = "Listar Todos os Chamados",
            description = "Recupere uma lista de todos os chamados cadastrados na aplicação."
    )
    @APIResponse(
            responseCode = "200",
            description = "Lista de chamados recuperada com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = SchemaType.ARRAY,
                            implementation = Atendente.class
                    )
            )
    )
    public Response all(){

        return Response.ok(chamadoRepository.listAll()).build();
    }
}
