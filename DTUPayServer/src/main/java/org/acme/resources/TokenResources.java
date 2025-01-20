package org.acme.resources;

import java.util.List;

import org.acme.exceptions.TokenException;
import org.acme.models.Token;
import org.acme.resources.dto.GenerateTokenRequest;
import org.acme.services.TokenService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tokens")


public class TokenResources {

    @Inject
    TokenService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Token> getAllTokens() {
        return service.getAllTokens();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void generateTokens(GenerateTokenRequest request) throws TokenException {
        service.generateToken(request.getCustomerId(), request.getCount());
    }

//    @DELETE
//    @Path("/{tokenId}")
//    public Response retireToken(@PathParam("tokenId") String tokenId) {
//        try {
//            service.markTokenAsUsed(tokenId);
//            return Response.noContent().build();
//        } catch (IllegalArgumentException e) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                        .entity(e.getMessage())
//                        .build();
//        }
//    }

    @GET
    @Path("/{tokenId}/validate")
    public Response validateToken(@PathParam("tokenId") String tokenId) throws TokenException {
            Token token = service.validateToken(tokenId);
            return Response.ok(token).build();   // 200 with token data
    }
}
