package resources;

import models.Token;
import models.TokenInt;
import services.TokenService;
import messaging.rabbitmq.token.TokenFactory;


import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/token")
public class TokenResource {

    TokenService service = TokenFactory.getService();

    @POST
    public Response requestTokens(TokenInt tokenInt) throws Exception {
        boolean successful = service.sendRequestTokensEvent(tokenInt);
        if (successful) {
            return Response.ok().build(); 
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Token request failed").build();
        }
    }

    @POST
    public Token getToken(String customerId) throws Exception{
        return service.sendGetTokenRequest(customerId);
    }

}