package br.com.helpgr.controller.chamado.get;

import br.com.helpgr.model.Chamado;
import br.com.helpgr.repositore.ChamadoRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Path("/chamado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ByClosedDate {

    @Inject
    ChamadoRepository chamadoRepository;

    @GET
    @Path("/dataEncer/{data}")
    public Response byClosedData(@PathParam("data") String data) {
        try {
            LocalDate dataEncerrado = LocalDate.parse(data);

            List<Chamado> chamados = chamadoRepository.list("dataEncerrado", dataEncerrado);

            if (chamados.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Nenhum chamado encontrado com a data de encerramento: " + data)
                        .build();
            }

            return Response.ok(chamados).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Formato de data inválido. Use o formato ISO (AAAA-MM-DD).")
                    .build();
        }
    }
}
