package br.com.helpgr.controller.atendente.get;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.repositore.AtendenteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/atendente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ByEmail {
    @Inject
    AtendenteRepository atendenteRepository;
    @GET
    @Path("/{email}")
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
