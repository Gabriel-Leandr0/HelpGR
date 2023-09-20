package br.com.helpgr.controller.chamado.get;

import br.com.helpgr.model.Chamado;
import br.com.helpgr.repositore.ChamadoRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/chamado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ByStatus {
    @Inject
    ChamadoRepository chamadoRepository;

    @GET
    @Path("/status/{status}")
    public Response byStatus(@PathParam("status") String status) {
        List<Chamado> chamados = chamadoRepository.list("status", status);

        if (chamados.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum chamado encontrado com o status: " + status)
                    .build();
        }

        return Response.ok(chamados).build();
    }
}
