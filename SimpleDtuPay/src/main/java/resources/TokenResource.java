package resources;

import jakarta.ws.rs.*;
import models.Token;
import models.TokenInt;
import services.TokenService;
import messaging.rabbitmq.token.TokenFactory;


import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/tokens")
public class TokenResource {

    TokenService service = TokenFactory.getService();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response requestTokens(TokenInt tokenInt) throws Exception {
        boolean successful = service.sendRequestTokensEvent(tokenInt);
        if (successful) {
            return Response.ok().build(); 
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Token request failed").build();
        }
    }

    //TODO check if this is correct   should be @GET
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Token getToken(String customerId) throws Exception{
        return service.sendGetTokenRequest(customerId);
    }

}