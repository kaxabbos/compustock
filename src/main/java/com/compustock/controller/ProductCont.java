package com.compustock.controller;

import com.compustock.controller.main.Attr;
import com.compustock.model.Ordering;
import com.compustock.model.OrderingDetail;
import com.compustock.model.Product;
import com.compustock.model.StatProduct;
import com.compustock.model.enums.OrderingStatus;
import com.compustock.model.enums.ProductStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductCont extends Attr {

    @GetMapping
    public String Products(Model model) {
        AddAttributesProducts(model);
        return "product";
    }

    @PostMapping("/add")
    public String ProductAdd(Model model, @RequestParam String name, @RequestParam int quantity, @RequestParam int unitPrice) {
        if (productService.findByName(name) != null) {
            AddAttributesProducts(model);
            model.addAttribute("message", "Продукт с наименованием \"" + name + "\" уже существует");
            return "product";
        }

        Product product = productService.addAndFlush(new Product(name, quantity, unitPrice));
        product.addStat(statProductService.addAndFlush(new StatProduct(quantity, DateNow(), null, ProductStatus.PRODUCED)));
        productService.update(product);
        return "redirect:/product";
    }

    @PostMapping("/{idProduct}/edit")
    public String ProductEdit(Model model, @PathVariable Long idProduct, @RequestParam String name, @RequestParam int quantity, @RequestParam int unitPrice) {
        Product productId = productService.find(idProduct);
        Product product = productService.findByName(name);

        if (product != null && !product.getName().equals(productId.getName())) {
            AddAttributesProducts(model);
            model.addAttribute("message", "Продукт с наименованием \"" + name + "\" уже существует");
            return "product";
        }

        product = productService.find(idProduct);

        if (quantity > product.getQuantity()) {
            product.addStat(statProductService.addAndFlush(new StatProduct(quantity - product.getQuantity(), DateNow(), null, ProductStatus.PRODUCED)));
        } else if (quantity < product.getQuantity()) {
            product.addStat(statProductService.addAndFlush(new StatProduct(product.getQuantity() - quantity, DateNow(), null, ProductStatus.SHIPPED)));
        }

        product.setQuantity(quantity);
        product.setUnitPrice(unitPrice);

        List<OrderingDetail> orderingDetailList = productService.find(idProduct).getDetails();
        Ordering ordering;
        for (OrderingDetail i : orderingDetailList) {
            ordering = orderingService.find(i.getOrdering().getId());
            if (ordering.getOrderingStatus() == OrderingStatus.NOT_RESERVED) {
                i.setPrice(i.getProduct().getUnitPrice() * i.getQuantity());
                setFullPriceAndFullQuantity(ordering.getId());
            }
            orderingDetailService.update(i);
        }

        product.setName(name);

        productService.update(product);
        return "redirect:/product";
    }

    @GetMapping("/{idProduct}/delete")
    public String ProductDelete(Model model, @PathVariable Long idProduct) {
        if (productService.find(idProduct).getDetails().size() != 0) {
            Product product = productService.find(idProduct);
            AddAttributesProducts(model);
            model.addAttribute("message", "Продукт \"" + product.getName() + "\" используется");
            return "product";
        }
        if (productService.find(idProduct).getQuantity() != 0) {
            AddAttributesProducts(model);
            Product product = productService.find(idProduct);
            model.addAttribute("message", "Продукт \"" + product.getName() + "\" еще не отгружен");
            return "product";
        }
        productService.delete(idProduct);
        return "redirect:/product";
    }
}
