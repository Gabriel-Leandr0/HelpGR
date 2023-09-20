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
public class ByOpeningDate {
    @Inject
    ChamadoRepository chamadoRepository;

    @GET
    @Path("/dataAbert/{data}")
    public Response byOpeningDate(@PathParam("data") String data) {
        try {
            LocalDate dataAbertura = LocalDate.parse(data);

            List<Chamado> chamados = chamadoRepository.list("dataAbertura", dataAbertura);

            if (chamados.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Nenhum chamado encontrado com a data de abertura: " + data)
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
