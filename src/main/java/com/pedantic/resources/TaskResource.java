package com.pedantic.resources;

import com.pedantic.entity.Task;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

@Path("tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    //https://foobar.com/tasks/api/v1/tasks

    @GET
//    @Produces(MediaType.APPLICATION_XML)
    public Response greet() {
        JsonObject jsonObject = Json.createObjectBuilder().add("course", "Java EE 8 Cookbook - mastering Restful Web services")
                .add("author", "Luqman Saeed")
                .add("pubYear", 2018).build();


        return Response.ok(jsonObject).build();
    }

    @POST
    @Path("saveObject")
    public Response saveGreet(Task task) {
//        System.out.println("Task ID: " + task.getId());


        System.out.println("Task Name: " + task.getTaskName());
        System.out.println("Task Due Date: "+ task.getDueDate());

        JsonObject build = Json.createObjectBuilder().add("taskReceived", task.getTaskName())
                .add("dateReceived", LocalDate.now().toString()).build();

        return Response.ok(build).build();
    }


    @Path("greeting")
    public String dontGreet() {
        return "Bla";
    }


}
