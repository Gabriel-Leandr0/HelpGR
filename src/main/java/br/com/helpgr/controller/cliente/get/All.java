package br.com.helpgr.controller.cliente.get;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.repository.ClienteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class All {

    @Inject
    ClienteRepository clienteRepository;

    @GET
    @Tag(name = "Clientes", description = "Gerencie os clientes da sua aplicação.")
    @Operation(
            summary = "Listar Todos os Clientes",
            description = "Recupere uma lista de todos os clientes cadastrados na aplicação."
    )
    @APIResponse(
            responseCode = "200",
            description = "Lista de clientes recuperada com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = SchemaType.ARRAY,
                            implementation = Atendente.class
                    )
            )
    )

    public Response all(){

        return Response.ok(clienteRepository.listAll()).build();
    }
}
