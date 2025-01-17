package org.acme.resources;

import java.util.List;

import org.acme.models.Token;
import org.acme.services.TokenService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/tokens")
public class TokenResources {

    // We have tokenId, CustomerId and state (used/unused)
    // each customer has at most 6 tokens
    @Inject
    TokenService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Token> Token() {

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void generateTokens( ){

    }
}
