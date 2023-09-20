package br.com.helpgr.controller.atendente.del;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.repositore.AtendenteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/atendente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ById {
    @Inject
    AtendenteRepository atendenteRepository;
    @DELETE
    @Path("/{id}")
    public Response byId(@PathParam("id") String id) {
        // Verifique se o atendente com o ID especificado existe
        Atendente atendenteExistente = atendenteRepository.findById(new ObjectId(id));

        if (atendenteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Atendente não encontrado com o ID: " + id)
                    .build();
        }

        // Execute a exclusão do atendente
        atendenteRepository.delete(atendenteExistente);

        return Response.noContent().build();
    }
}
