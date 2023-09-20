package br.com.helpgr.controller.chamado.put;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.model.Chamado;
import br.com.helpgr.repositore.AtendenteRepository;
import br.com.helpgr.repositore.ChamadoRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import java.time.LocalDate;

@Path("/chamado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ById {

    @Inject
    ChamadoRepository chamadoRepository;
    @Inject
    AtendenteRepository atendenteRepository;

    @PUT
    @Path("/{id}")
    public Response byId(
            @PathParam("id") String id, // O ID do chamado a ser atualizado
            Chamado chamadoAtualizado // O objeto com os dados atualizados
    ) {
        // Verifique se o chamado com o ID especificado existe
        Chamado chamadoExistente = chamadoRepository.findById(new ObjectId(id));
        if (chamadoExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Chamado não encontrado com o ID: " + id)
                    .build();
        }

        String atendenteEmail = chamadoAtualizado.getAtendenteEmail();
        Atendente atendenteExistente = atendenteRepository.findByEmail(atendenteEmail);

        // Verifica se o email do atendente existe
        if (atendenteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Atendente não encontrado com o E-mail: " + atendenteEmail)
                    .build();
        }

        // Verifique se o status foi alterado para "encerrado"
        String novoStatus = chamadoAtualizado.getStatus();
        if ("encerrado".equalsIgnoreCase(novoStatus) && !"encerrado".equalsIgnoreCase(chamadoExistente.getStatus())) {
            // O status foi alterado para "encerrado", portanto, atualize a dataEncerrado com a data atual
            chamadoExistente.setDataEncerrado(LocalDate.now());
        }

        // Atualize os campos relevantes do chamado existente com os dados do chamado atualizado
        chamadoExistente.setSolucao(chamadoAtualizado.getSolucao());
        chamadoExistente.setAtendenteEmail(chamadoAtualizado.getAtendenteEmail());
        chamadoExistente.setStatus(novoStatus);

        // Salve o chamado atualizado no banco de dados
        chamadoRepository.update(chamadoExistente);

        return Response.ok(chamadoExistente).build();
    }

}
