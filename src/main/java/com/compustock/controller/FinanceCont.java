package com.compustock.controller;

import com.compustock.controller.main.Attr;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stat")
public class FinanceCont extends Attr {

    @GetMapping
    public String Stat(Model model) {
        AddAttributesStat(model);
        return "stat";
    }
}
