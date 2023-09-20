package br.com.helpgr.controller.chamado.get;

import br.com.helpgr.repositore.ChamadoRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/chamado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class All {
    @Inject
    ChamadoRepository chamadoRepository;
    @GET
    public Response all(){

        return Response.ok(chamadoRepository.listAll()).build();
    }
}
