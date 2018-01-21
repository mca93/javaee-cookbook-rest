package com.pedantic.resources;

import com.pedantic.entity.User;
import com.pedantic.services.UserService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {

    @Inject
    private UserService userService;

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

        userService.saveUser(user);



        return Response.ok().build();

    }


    @GET
    @Path("list")
    public List<User> getUsers() {
        return userService.getUsers();
    }

}
