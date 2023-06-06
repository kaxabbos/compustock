package com.compustock.controller;

import com.compustock.controller.main.Attr;
import com.compustock.model.Ordering;
import com.compustock.model.OrderingDetail;
import com.compustock.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ordering")
public class OrderInfoCont extends Attr {

    @GetMapping("/{idOrder}/orderingDetail")
    public String OrderingDetail(Model model, @PathVariable Long idOrder) {
        AddAttributesOrderDetails(model, idOrder);
        setFullPriceAndFullQuantity(idOrder);
        return "orderingDetail";
    }

    @PostMapping("/{idOrder}/orderingDetail/add")
    public String OrderingDetailAdd(Model model, @PathVariable Long idOrder, @RequestParam Long idProduct) {
        if (productService.find(idProduct).getQuantity() == 0) {
            AddAttributesOrderDetails(model, idOrder);
            model.addAttribute("message", "Этого продукта нету в наличии");
            return "orderingDetail";
        }

        for (OrderingDetail i : orderingService.find(idOrder).getDetails()) {
            if (i.getProduct().getId().equals(idProduct)) {
                AddAttributesOrderDetails(model, idOrder);
                model.addAttribute("message", "Вы не можете добавить несколько раз один и тот же продукт");
                return "orderingDetail";
            }
        }

        OrderingDetail orderingDetail = orderingDetailService.addAndFlush(new OrderingDetail());

        Product product = productService.find(idProduct);
        product.addDetail(orderingDetail);
        productService.update(product);

        Ordering ordering = orderingService.find(idOrder);
        ordering.addDetail(orderingDetail);
        orderingService.update(ordering);

        return "redirect:/ordering/{idOrder}/orderingDetail";
    }

    @PostMapping("/{idOrder}/orderingDetail/{idOrderDetail}/edit")
    public String OrderingDetailEdit(Model model, @PathVariable Long idOrder, @PathVariable Long idOrderDetail, @RequestParam Long idProduct, @RequestParam int quantity) {
        OrderingDetail orderingDetail = orderingDetailService.find(idOrderDetail);

        if (!orderingDetail.getProduct().getId().equals(idProduct)) {
            List<OrderingDetail> orderingDetailList = orderingService.find(idOrder).getDetails();

            for (OrderingDetail i : orderingDetailList) {
                if (i.getProduct().getId().equals(idProduct)) {
                    AddAttributesOrderDetails(model, idOrder);
                    model.addAttribute("message", "Вы не можете добавить несколько раз один и тот же продукт");
                    return "orderingDetail";
                }
            }
            Product product = productService.find(idProduct);
            product.removeDetail(orderingDetailService.find(idOrderDetail));
            product.addDetail(orderingDetail);
        }

        if (orderingDetail.getProduct().getQuantity() < quantity)
            orderingDetail.setQuantity(orderingDetail.getProduct().getQuantity());
        else orderingDetail.setQuantity(quantity);

        orderingDetail.setPrice(orderingDetail.getProduct().getUnitPrice() * orderingDetail.getQuantity());

        orderingDetailService.update(orderingDetail);

        return "redirect:/ordering/{idOrder}/orderingDetail";
    }

    @GetMapping("/{idOrder}/orderingDetail/{idOrderDetail}/delete")
    public String OrderingDetailDelete(@PathVariable Long idOrder, @PathVariable Long idOrderDetail) {
        Ordering ordering = orderingService.find(idOrder);
        ordering.removeDetail(orderingDetailService.find(idOrderDetail));
        orderingService.update(ordering);
        return "redirect:/ordering/{idOrder}/orderingDetail";
    }
}