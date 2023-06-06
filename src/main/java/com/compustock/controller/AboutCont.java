package com.compustock.controller;

import com.compustock.controller.main.Attr;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutCont extends Attr {

    @GetMapping
    public String about(Model model) {
        MainAttr(model);
        return "about";
    }
}
