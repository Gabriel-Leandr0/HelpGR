package br.com.helpgr.controller.atendente.post;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.repositore.AtendenteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/atendente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class Create {
    @Inject
    AtendenteRepository atendenteRepository;
    @POST
    public Response create(Atendente atendente) throws URISyntaxException {
        atendenteRepository.persist(atendente);
        return Response.created(new URI("/" + atendente.id)).build();
    }
}
