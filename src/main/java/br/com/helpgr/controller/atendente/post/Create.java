package br.com.helpgr.controller.atendente.post;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.repository.AtendenteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.net.URISyntaxException;

@Path("/atendente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class Create {
    @Inject
    AtendenteRepository atendenteRepository;
    @POST
    @Tag(name = "Atendentes", description = "Gerencie os atendentes da sua aplicação.")
    @Operation(
            summary = "Criar Atendente",
            description = "Crie um novo atendente na aplicação."
    )
    @RequestBody(
            description = "Detalhes do novo atendente a ser criado.",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Atendente.class)
            )
    )
    @APIResponse(
            responseCode = "201",
            description = "Atendente criado com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Atendente.class)
            )
    )

    public Response create(Atendente atendente) throws URISyntaxException {
        atendenteRepository.persist(atendente);


        return Response.created(new URI("/" + atendente.id)).entity("Atendente criado!").build();
    }
}
