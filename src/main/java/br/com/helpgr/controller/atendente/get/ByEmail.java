package br.com.helpgr.controller.atendente.get;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.repository.AtendenteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/atendente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ByEmail {
    @Inject
    AtendenteRepository atendenteRepository;
    @GET
    @Path("/{email}")
    @Tag(name = "Atendentes", description = "Gerencie os atendentes da sua aplicação.")
    @Operation(
            summary = "Buscar Atendente por E-mail",
            description = "Recupere um atendentes com base no seu endereço de e-mail."
    )
    @APIResponse(
            responseCode = "200",
            description = "Atendente encontrado com sucesso.",
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

    public Response byEmail(@PathParam("email") String email) {
        Atendente atendente = atendenteRepository.findByEmail(email);

        if (atendente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Atendente não encontrado com o E-mail: " + email)
                    .build();
        }

        return Response.ok(atendente).build();
    }
}
