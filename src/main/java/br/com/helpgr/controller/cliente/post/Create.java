package br.com.helpgr.controller.cliente.post;

import br.com.helpgr.model.Cliente;
import br.com.helpgr.repositore.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;

@Path("/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Create {

    @Inject
    ClienteRepository clienteRepository;

    @POST
    public Response create(Cliente cliente) throws URISyntaxException {
        clienteRepository.persist(cliente);
        return Response.created(new URI("/" + cliente.id)).build();
    }

}
