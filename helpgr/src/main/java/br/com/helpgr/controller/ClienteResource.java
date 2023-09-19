package br.com.helpgr.controller;


import br.com.helpgr.model.Cliente;
import br.com.helpgr.repositore.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {
    @Inject
    ClienteRepository clienteRepository;

    //Cria um cliente
    @POST
    public Response create(Cliente cliente) throws URISyntaxException {
        clienteRepository.persist(cliente);
            return Response.created(new URI("/" + cliente.id)).build();
    }


    //Lista todos os clientes
    @GET
    public Response searchAll(){

        return Response.ok(clienteRepository.listAll()).build();
    }

    //Busca cliente por Email
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


    //Atualiza cadastro do cliente
    @PUT
    @Path("/{email}")
    public Response atualizarCliente(@PathParam("email") String email, Cliente clienteAtualizado) {
        // Busque o cliente existente pelo Email
        Cliente clienteExistente = clienteRepository.findByEmail(email);

        if (clienteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente não encontrado com o E-mail: " + email)
                    .build();
        }


        // Campos que serão atualizados
        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setEmail(clienteAtualizado.getEmail());

        clienteRepository.update(clienteExistente);

        return Response.ok(clienteExistente).build();
    }

 //DELETA O CLIENTE
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
