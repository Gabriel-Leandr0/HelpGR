package br.com.helpgr.controller.atendente.get;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.repositore.AtendenteRepository;
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

import java.util.List;

@Path("/atendente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class All {

    @Inject
    AtendenteRepository atendenteRepository;
    @GET
    @Tag(name = "Atendentes", description = "Gerencie os atendentes da sua aplicação.")
    @Operation(
            summary = "Listar Todos os Atendentes",
            description = "Recupere uma lista de todos os atendentes cadastrados na aplicação."
    )
    @APIResponse(
            responseCode = "200",
            description = "Lista de atendentes recuperada com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = SchemaType.ARRAY,
                            implementation = Atendente.class
                    )
            )
    )
    public Response all(){

        return Response.ok(atendenteRepository.listAll()).build();
    }
}
