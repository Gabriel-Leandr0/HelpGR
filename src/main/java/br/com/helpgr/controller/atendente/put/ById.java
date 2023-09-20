package br.com.helpgr.controller.atendente.put;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.repositore.AtendenteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/atendente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ById {

    @Inject
    AtendenteRepository atendenteRepository;

    @PUT
    @Path("/{email}")
    public Response byId(@PathParam("email") String email, Atendente atendenteAtualizado) {
        // Busque o cliente existente pelo Email
        Atendente atendenteExistente = atendenteRepository.findByEmail(email);

        if (atendenteExistente == null) {
            return Response.status(Status.NOT_FOUND)
                    .entity("Cliente não encontrado com o E-mail: " + email)
                    .build();
        }


        // Campos que serão atualizados
        atendenteExistente.setNome(atendenteAtualizado.getNome());
        atendenteExistente.setEmail(atendenteAtualizado.getEmail());

        atendenteRepository.update(atendenteExistente);

        return Response.ok(atendenteExistente).build();
    }
}
