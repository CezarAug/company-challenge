package com.companytest.citydomain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${general.endpoint}")
public class CityController {

    @GetMapping
    String getMessage() {
        return "hello";
    }
}
