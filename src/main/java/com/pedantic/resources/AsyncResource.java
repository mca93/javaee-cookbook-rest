package com.pedantic.resources;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("async")
public class AsyncResource {

    @Resource
    ManagedExecutorService managedExecutorService;
    @Inject
    Logger logger;


    @GET
    @Path("salaries")
    public void asyncResponse(@Suspended AsyncResponse asyncResponse) {
        String initialThread = Thread.currentThread().getName();
        logger.log(Level.INFO, "Initial thread name is {0}", initialThread);

        managedExecutorService.execute(() -> {


            try {

                String newThread = Thread.currentThread().getName();

                logger.log(Level.INFO, "New thread name is {0}", newThread);
                Thread.sleep(5000);

                String conclusion = "Processing started in " + initialThread + " and has been completed in thread "
                        + newThread;

                logger.log(Level.INFO, conclusion);

                asyncResponse.resume(Response.ok(conclusion).build());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        });
    }

    @GET
    @Path("payroll")
    public CompletionStage<Response> getSalaries() {
        CompletableFuture<Response> completableFuture = new CompletableFuture<>();

        String initialThread = Thread.currentThread().getName();
        logger.log(Level.INFO, "Initial thread name is {0}", initialThread);


        managedExecutorService.submit(()->{
            try {
                String newThread = Thread.currentThread().getName();

                logger.log(Level.INFO, "New thread name is {0}", newThread);

                Thread.sleep(6000);
                String conclusion = "Processing started in " + initialThread + " and has been completed in thread "
                        + newThread;
                completableFuture.complete(Response.ok(conclusion).build());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        return completableFuture;
    }
}
