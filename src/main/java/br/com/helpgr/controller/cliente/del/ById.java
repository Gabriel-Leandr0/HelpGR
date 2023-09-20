package br.com.helpgr.controller.cliente.del;

import br.com.helpgr.model.Cliente;
import br.com.helpgr.repositore.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ById {

    @Inject
    ClienteRepository clienteRepository;

    @DELETE
    @Path("/{id}")
    public Response deleteCliente(@PathParam("id") String id) {
        // Verifique se o cliente com o ID especificado existe
        Cliente clienteExistente = clienteRepository.findById(new ObjectId(id));

        if (clienteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente não encontrado com o ID: " + id)
                    .build();
        }

        // Execute a exclusão do cliente
        clienteRepository.delete(clienteExistente);

        return Response.noContent().build();
    }

}
