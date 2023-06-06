package com.compustock.controller;

import com.compustock.controller.main.Attr;
import com.compustock.model.enums.ProductStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/statProduct")
public class StatProductCont extends Attr {

    @GetMapping
    public String statProd(Model model) {
        AddAttributesStatProduct(model, ProductStatus.ALL, null);
        return "statProduct";
    }

    @PostMapping("/search")
    public String statProdSearch(Model model, @RequestParam ProductStatus productStatus, @RequestParam String date) {
        AddAttributesStatProduct(model, productStatus, date);
        return "statProduct";
    }
}
