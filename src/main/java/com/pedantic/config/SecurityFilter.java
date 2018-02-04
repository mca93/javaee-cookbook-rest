package com.pedantic.config;

import com.pedantic.services.MySessionStore;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Secure
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {
    private final String BEARER = "Bearer";

    @Inject
    SecurityUtil securityUtil;
    @Inject
    Logger logger;
    @Inject
    MySessionStore mySessionStore;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        //Check request header for a bearer token
        //If none is found, return error to client
        //If a token is found, parse using signing key
        // If parsing succeeds, execution should continue
        //if parsing fails, throw an error
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            logger.log(Level.SEVERE, "Error! No bearer token found! {0}", authHeader);
            throw new NotAuthorizedException("You are not authorized to access this resource. Please log in");
        }

        String token = authHeader.substring(BEARER.length()).trim();
        Key key = securityUtil.generateKey(mySessionStore.getEncodedPassword());

        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Invalid bearer token {0}", token);

            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
        }


    }
}
