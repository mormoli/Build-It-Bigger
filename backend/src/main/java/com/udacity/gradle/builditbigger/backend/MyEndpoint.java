package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import gradle.udacity.com.javajokes.Joker;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /* A simple endpoint method that returns java joke library object */
    @ApiMethod(name = "jokeTeller")
    public Joker jokeTeller() {
        Joker joker = new Joker();
        joker.setJoke(joker.getJoke());
        return joker;
    }

}
