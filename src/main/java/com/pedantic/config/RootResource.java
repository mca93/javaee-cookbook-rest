package com.pedantic.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api/v1")
public class RootResource extends Application {
    //https://foobar.com/tasks/api/v1/
}
