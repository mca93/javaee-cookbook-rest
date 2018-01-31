package com.pedantic.resources;

import com.pedantic.entity.User;
import com.pedantic.services.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


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

    @POST
    @Path("pic")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "image/png", "image/jpg", "image/jpeg"})
    public Response saveUserPicture(@QueryParam("id") @Valid @NotNull Long id, File file) {
        User user = userService.getUserById(id);

        try {
            if (user != null) {
                user.setPicture(Files.readAllBytes(Paths.get(file.toURI())));
                String extension = userService.getFileExtension(file);
                System.out.println("file extension is " + extension);
                user.setFileName(UUID.randomUUID() + extension);
                userService.saveUser(user);
                return Response.ok(user).build();

            }

            return Response.status(Response.Status.NO_CONTENT).build();

        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }


    }

    @GET
    @Path("user-pic")
    @Produces({MediaType.APPLICATION_OCTET_STREAM, "image/png", "image/jpg"})
    public Response getPicture(@QueryParam("id") @Valid @NotNull(message = "user id must be set") Long id) throws IOException {
        User user = userService.getUserById(id);

        if (user != null) {
            return Response.ok().entity(Files.write(Paths.get(user.getFileName()), user.getPicture()).toFile()).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();

        }


    }


    @GET
    @Path("list")
    public List<User> getUsers() {
        return userService.getUsers();
    }

}
