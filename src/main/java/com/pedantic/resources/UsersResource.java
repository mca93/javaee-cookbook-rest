package com.pedantic.resources;

import com.pedantic.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.awt.*;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {

   //1. How to grab form values (hardest way)
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response saveUser(@FormParam("nameOfUser") String nameOfUser,
                             @FormParam("email") String email,
                             @FormParam("password") String password)
     {


        return Response.ok().build();
    }


    //2. How to grab form values (semi-hard way)
    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createUser(MultivaluedMap<String, String> mapValues) {

        mapValues.get("email");

        return Response.ok().build();

    }


    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createNewUser(@BeanParam User user) {



        return Response.ok().build();

    }

}
