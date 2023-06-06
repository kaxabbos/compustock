package com.compustock.controller;

import com.compustock.controller.main.Attr;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ordering")
public class InfoCont extends Attr {

    @GetMapping("/{idOrdering}/detail")
    public String Details(Model model, @PathVariable Long idOrdering) {
        AddAttributesDetails(model, idOrdering);
        return "detail";
    }
}
