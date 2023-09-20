package br.com.helpgr.controller.atendente.get;

import br.com.helpgr.repositore.AtendenteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/atendente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class All {
    @Inject
    AtendenteRepository atendenteRepository;
    @GET
    public Response all(){

        return Response.ok(atendenteRepository.listAll()).build();
    }
}
