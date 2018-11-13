package com.codechallenge.itineraryservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${general.endpoint}")
public class ItineraryController {

    @GetMapping
    String getMessage() {
        return "hello, i'm the second service here";
    }
}
