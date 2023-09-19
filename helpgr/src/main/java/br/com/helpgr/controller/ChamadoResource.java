package br.com.helpgr.controller;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.model.Chamado;
import br.com.helpgr.model.Cliente;
import br.com.helpgr.repositore.AtendenteRepository;
import br.com.helpgr.repositore.AtendenteRepository;
import br.com.helpgr.repositore.ChamadoRepository;
import br.com.helpgr.repositore.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Path("/chamado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChamadoResource {
    @Inject
    ChamadoRepository chamadoRepository;
    @Inject
    ClienteRepository clienteRepository;
    @Inject
    AtendenteRepository atendenteRepository;

//CRIA CHAMADO
@POST
public Response create(Chamado chamado) throws URISyntaxException {
    // Verifique se o cliente associado existe pelo email
    if (chamado.clienteEmail != null) {
        Cliente clienteExistente = clienteRepository.findByEmail(chamado.clienteEmail);
        if (clienteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente não encontrado com o E-mail: " + chamado.clienteEmail)
                    .build();
        }
    }

    // Defina a data de abertura como a data e hora atuais
    chamado.setDataAbertura(LocalDate.now());

    chamadoRepository.persist(chamado);
    return Response.created(new URI("/" + chamado.id)).build();
}

//LISTA TODOS OS CHAMADOS
    @GET
    public Response searchAll(){

        return Response.ok(chamadoRepository.listAll()).build();
    }

//BUSCA CHAMADO ATRAVÉS DO EMAIL DO CLIENTE OU DO ATENDENTE
@GET
@Path("/{email}")
public Response findChamadosByEmail(@PathParam("email") String email) {
    Cliente cliente = clienteRepository.findByEmail(email);
    Atendente atendente = atendenteRepository.findByEmail(email);

    List<Chamado> chamados = new ArrayList<>();

    if (cliente != null) {
        // Se o email pertence a um cliente, procuramos os chamados associados ao cliente
        chamados = chamadoRepository.find("clienteEmail", email).list();
    } else if (atendente != null) {
        // Se o email pertence a um atendente, procuramos os chamados associados ao atendente
        chamados = chamadoRepository.find("atendenteEmail", email).list();
    }

    if (chamados.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Nenhum chamado encontrado para o E-mail: " + email)
                .build();
    }

    return Response.ok(chamados).build();
}

//BUSCA POR STATUS
    @GET
    @Path("/status/{status}")
    public Response findChamadosByStatus(@PathParam("status") String status) {
        List<Chamado> chamados = chamadoRepository.list("status", status);

        if (chamados.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Nenhum chamado encontrado com o status: " + status)
                    .build();
        }

        return Response.ok(chamados).build();
    }

//BUSCA POR DATA DE ABERTURA
@GET
@Path("/dataAbert/{data}")
public Response findByDataAbertura(@PathParam("data") String data) {
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

//BUSCA POR DATA DE ENCERRAMENTO
    @GET
    @Path("/dataEncer/{data}")
    public Response findByDataEncerrado(@PathParam("data") String data) {
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

//ATUALIZA CHAMADO ATRAVÉS DO ID DO CHAMADO
    @PUT
    @Path("/{id}")
    public Response atualizarChamado(
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

 //DELETAR CHAMADO
     @DELETE
     @Path("/{id}")
     public Response deleteChamado(@PathParam("id") String id) {
         // Verifique se o chamado com o ID especificado existe
         Chamado chamadoExistente = chamadoRepository.findById(new ObjectId(id));

         if (chamadoExistente == null) {
             return Response.status(Response.Status.NOT_FOUND)
                     .entity("Chamado não encontrado com o ID: " + id)
                     .build();
         }

         // Execute a exclusão do chamado
         chamadoRepository.delete(chamadoExistente);

         return Response.noContent().build();
     }

}
