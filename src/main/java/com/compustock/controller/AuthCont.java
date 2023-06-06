package com.compustock.controller;

import com.compustock.controller.main.Attr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class AuthCont extends Attr {

    @GetMapping
    public String login() {
        return "login";
    }
}
