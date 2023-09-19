package br.com.helpgr.controller;

import br.com.helpgr.model.Atendente;
import br.com.helpgr.repositore.AtendenteRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/atendente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AtendenteResource {

    @Inject
    AtendenteRepository atendenteRepository;

    @POST
    public Response create(Atendente atendente) throws URISyntaxException {
        atendenteRepository.persist(atendente);
        return Response.created(new URI("/" + atendente.id)).build();
    }
    @GET
    public Response searchAll(){

        return Response.ok(atendenteRepository.listAll()).build();
    }
//BUSCA ATENDENTE ATRAVÉS DO E-MAIL
    @GET
    @Path("/{email}")
    public Response findByEmail(@PathParam("email") String email) {
        Atendente atendente = atendenteRepository.findByEmail(email);

        if (atendente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Atendente não encontrado com o E-mail: " + email)
                    .build();
        }

        return Response.ok(atendente).build();
    }

 //DELETAR ATENDENTE
     @DELETE
     @Path("/{id}")
     public Response deleteAtendente(@PathParam("id") String id) {
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


