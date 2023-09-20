package br.com.helpgr.controller.chamado.get;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.model.Chamado;
import br.com.helpgr.model.Cliente;
import br.com.helpgr.repositore.AtendenteRepository;
import br.com.helpgr.repositore.ChamadoRepository;
import br.com.helpgr.repositore.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;

@Path("/chamado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ByEmail {

    @Inject
    ChamadoRepository chamadoRepository;
    @Inject
    ClienteRepository clienteRepository;
    @Inject
    AtendenteRepository atendenteRepository;

    @GET
    @Path("/{email}")
    @Tag(name = "Chamados", description = "Gerencie os chamados da sua aplicação.")
    @Operation(
            summary = "Buscar Chamados por Email",
            description = "Recupere uma lista de chamados associados a um cliente ou atendente com base no endereço de e-mail."
    )
    @Parameter(
            name = "email",
            in = ParameterIn.PATH,
            required = true,
            description = "Endereço de e-mail do cliente ou atendente."
    )
    @APIResponse(
            responseCode = "200",
            description = "Chamados encontrados com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Chamado.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "Nenhum chamado encontrado",
            content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(implementation = String.class)
            )
    )

    public Response byEmail(@PathParam("email") String email) {

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
}
