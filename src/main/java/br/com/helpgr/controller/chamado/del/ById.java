package br.com.helpgr.controller.chamado.del;

import br.com.helpgr.model.Chamado;
import br.com.helpgr.repositore.ChamadoRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/chamado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ById {

    @Inject
    ChamadoRepository chamadoRepository;

    @DELETE
    @Path("/{id}")
    public Response byId(@PathParam("id") String id) {

        // Verifica se o chamado com o ID especificado existe
        Chamado chamadoExistente = chamadoRepository.findById(new ObjectId(id));

        if (chamadoExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Chamado não encontrado com o ID: " + id)
                    .build();
        }

        // Executa a exclusão do chamado
        chamadoRepository.delete(chamadoExistente);

        return Response.noContent().build();
    }
}
