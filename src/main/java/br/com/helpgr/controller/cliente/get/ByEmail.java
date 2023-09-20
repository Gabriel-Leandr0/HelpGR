package br.com.helpgr.controller.cliente.get;

import br.com.helpgr.model.Cliente;
import br.com.helpgr.repositore.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ByEmail {

    @Inject
    ClienteRepository clienteRepository;

    @GET
    @Path("/{email}")
    public Response findByEmail(@PathParam("email") String email) {
        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente não encontrado com o E-mail: " + email)
                    .build();
        }

        return Response.ok(cliente).build();
    }

}
