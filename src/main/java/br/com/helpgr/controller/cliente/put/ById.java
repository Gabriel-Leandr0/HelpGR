package br.com.helpgr.controller.cliente.put;

import br.com.helpgr.model.Cliente;
import br.com.helpgr.repositore.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ById {

    @Inject
    ClienteRepository clienteRepository;

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
}
