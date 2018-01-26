package com.pedantic.resources;

import com.pedantic.entity.User;
import com.pedantic.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {

    @Inject
    private UserService userService;

   //1. How to grab form values (hardest way)
//    @POST
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public Response saveUser(@FormParam("nameOfUser") String nameOfUser,
//                             @FormParam("email") String email,
//                             @FormParam("password") String password)
//     {
//
//
//        return Response.ok().build();
//    }


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
    public Response createNewUser(@BeanParam @Valid User user) {

        userService.saveUser(user);



        return Response.ok().build();

    }

    @POST
    @Path("validation")
    public Response saveNewUser(@Valid User user) {
        userService.saveUser(user);

        NewCookie cookie = new NewCookie("User created", user.getId().toString());
        NewCookie cookie1 = new NewCookie("URL", "Hey I am the path to the resource.");

        return Response.ok(user).cookie(cookie, cookie1).build();
    }

    @GET
    @Path("{id}")
    public User getUser(@PathParam("id") Long id) {
       return userService.getUserById(id);
    }

    @GET
    @Path("list")
    public List<User> getUsers() {
        return userService.getUsers();
    }

}
