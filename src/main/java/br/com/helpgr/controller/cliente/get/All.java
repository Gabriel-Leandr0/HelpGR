package br.com.helpgr.controller.cliente.get;

import br.com.helpgr.repositore.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class All {

    @Inject
    ClienteRepository clienteRepository;

    @GET
    public Response searchAll(){

        return Response.ok(clienteRepository.listAll()).build();
    }
}
