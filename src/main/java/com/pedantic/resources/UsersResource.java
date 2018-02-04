package com.pedantic.resources;

import com.pedantic.config.Secure;
import com.pedantic.config.SecurityUtil;
import com.pedantic.entity.User;
import com.pedantic.services.MySessionStore;
import com.pedantic.services.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.inject.Inject;
import javax.resource.spi.SecurityException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {
    @Context
    UriInfo uriInfo;
    @Inject
    private UserService userService;
    @Inject
    private SecurityUtil securityUtil;
    @Inject
    private MySessionStore mySessionStore;

    @POST
    @Path("register")
    public Response registerNewUser(@Valid  User user) {
        userService.saveUser(user);
        return Response.ok(user).build();
    }

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
//    @POST
//    @Path("create")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public Response createUser(MultivaluedMap<String, String> mapValues) {
//
//        mapValues.get("email");
//
//        return Response.ok().build();
//
//    }


//    @POST
//    @Path("new")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public Response createNewUser(@BeanParam @Valid User user) {
//
//        userService.saveUser(user);
//
//
//        return Response.ok().build();
//
//    }

//    @POST
//    @Path("validation")
//    public Response saveNewUser(@Valid User user) {
//        userService.saveUser(user);
//
//        NewCookie cookie = new NewCookie("User created", user.getId().toString());
//        NewCookie cookie1 = new NewCookie("URL", "Hey I am the path to the resource.");
//
//
//        return Response.ok(user).cookie(cookie, cookie1).build();
//    }

    @GET
    @Path("{id}")
    @Secure
    public User getUser(@PathParam("id") Long id) {

        return userService.getUserById(id);
    }

//    @POST
//    @Path("pic")
//    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "image/png", "image/jpg", "image/jpeg"})
//    public Response saveUserPicture(@QueryParam("id") @Valid @NotNull Long id, File file) {
//        User user = userService.getUserById(id);
//
//        try {
//            if (user != null) {
//                user.setPicture(Files.readAllBytes(Paths.get(file.toURI())));
//                String extension = userService.getFileExtension(file);
//                System.out.println("file extension is " + extension);
//                user.setFileName(UUID.randomUUID() + extension);
//                userService.saveUser(user);
//                return Response.ok(user).build();
//
//            }
//
//            return Response.status(Response.Status.NO_CONTENT).build();
//
//        } catch (IOException e) {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//        }
//
//
//    }

//    @GET
//    @Path("user-pic")
//    @Produces({MediaType.APPLICATION_OCTET_STREAM, "image/png", "image/jpg"})
//    public Response getPicture(@QueryParam("id") @Valid @NotNull(message = "user id must be set") Long id) throws IOException {
//        User user = userService.getUserById(id);
//
//        if (user != null) {
//            return Response.ok().entity(Files.write(Paths.get(user.getFileName()), user.getPicture()).toFile()).build();
//        } else {
//            return Response.status(Response.Status.NO_CONTENT).build();
//
//        }
//
//
//    }


    @GET
    @Path("list")
    @Secure
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("email") @NotEmpty(message = "user name must be set") String email,
                          @FormParam("password") @NotEmpty(message = "user name must be set") String password) {


        try {
            authenticate(email, password);
            String token = generateToken(email, password);


            return Response.ok().header(HttpHeaders.AUTHORIZATION, token).build();
            //Generate JWT token
            //Pass token in response to client

        } catch (SecurityException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        //generate a JWT token to send to the user
        //send an error message to the user
    }


    private void authenticate(String email, String password) throws SecurityException {
        //query for user from DB with given credentials
        //if user not found, throw security exception.

        User user = userService.findUserByCredentials(email, password);
        if (user == null) {
            throw new SecurityException("Incorrect credentials passed");
        }

    }


    private String generateToken(String email, String password) {
//Key to sign
        //Token
        String encodedPassword = securityUtil.encodeText(password);

        mySessionStore.setEncodedPassword(encodedPassword);
        mySessionStore.setEmail(email);


        Key key = securityUtil.generateKey(encodedPassword);

        return Jwts.builder().setSubject(email).setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date()).setExpiration(toDate(LocalDateTime.now().plusMinutes(15)))
                .signWith(SignatureAlgorithm.HS512, key).setAudience(uriInfo.getBaseUri().toString())
                .compact();

    }


    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
