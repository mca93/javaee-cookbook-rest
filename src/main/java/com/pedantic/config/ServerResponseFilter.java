package com.pedantic.config;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class ServerResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
//check if method is get
        //tell client to cache using cache control abstraction for 150 secs
        if (requestContext.getMethod().equals("GET")) {
            CacheControl cacheControl = new CacheControl();
            cacheControl.setMaxAge(150);
//            responseContext.getHeaders().add("Cache-Control", cacheControl);

        }

    }
}
