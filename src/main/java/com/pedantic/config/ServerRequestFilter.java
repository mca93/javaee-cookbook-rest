package com.pedantic.config;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@PreMatching
public class ServerRequestFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        //X-HTTP-METHOD - POST

        String methodOverride = requestContext.getHeaderString("X-Http-Method-Override");
        if (methodOverride != null && !methodOverride.isEmpty()) {
            requestContext.setMethod(methodOverride);

        }
    }
}
