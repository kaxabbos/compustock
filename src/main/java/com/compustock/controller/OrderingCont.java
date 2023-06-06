package com.compustock.controller;

import com.compustock.controller.main.Attr;
import com.compustock.model.*;
import com.compustock.model.enums.OrderingStatus;
import com.compustock.model.enums.PaymentType;
import com.compustock.model.enums.ProductStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ordering")
public class OrderingCont extends Attr {
    @GetMapping
    public String Orders(Model model) {
        AddAttributesOrdering(model);
        return "ordering";
    }


    @PostMapping("/add")
    public String OrderAdd(Model model, @RequestParam Long clientId, @RequestParam String date) {
        Client client = clientService.find(clientId);

        List<Ordering> orderingList = client.getOrderings();
        for (Ordering i : orderingList) {
            if (i.getDate().equals(date)) {
                if (i.getOrderingStatus() == OrderingStatus.NOT_RESERVED) {
                    AddAttributesOrdering(model);
                    model.addAttribute("message", "Заказ для клиента \"" + client.getFio() + "\" с датой \"" + date + "\" в оформлении");
                    return "ordering";
                }
            }
        }

        Ordering ordering = orderingService.addAndFlush(new Ordering(date));
        client.addOrdering(ordering);
        clientService.update(client);

        orderingService.update(ordering);

        return "redirect:/ordering";
    }

    @GetMapping("/{idOrder}/reserved")
    public String OrderArrange(Model model, @PathVariable Long idOrder) {
        Ordering ordering = orderingService.find(idOrder);

        List<OrderingDetail> orderingDetailList = ordering.getDetails();

        if (orderingDetailList.size() == 0) {
            AddAttributesOrdering(model);
            model.addAttribute("message", "Не выбраны детали заказа: " + ordering.getClient().getFio());
            return "ordering";
        }

        for (OrderingDetail i : orderingDetailList) {
            Product product = productService.find(i.getProduct().getId());
            if (i.getQuantity() > product.getQuantity()) {
                AddAttributesOrdering(model);
                model.addAttribute("message", "Недостаточно товаров для заказа: " + ordering.getClient().getFio());
                return "ordering";
            }
            if (i.getQuantity() == 0) {
                AddAttributesOrdering(model);
                model.addAttribute("message", "Неккоректный выбор деталей заказа: " + ordering.getClient().getFio());
                return "ordering";
            }
        }

        for (OrderingDetail i : orderingDetailList) {
            Product product = productService.find(i.getProduct().getId());
            product.setQuantity(product.getQuantity() - i.getQuantity());
            product.addStat(statProductService.addAndFlush(new StatProduct(i.getQuantity(), DateNow(), i.getId(), ProductStatus.RESERVED)));
            productService.update(product);
        }

        ordering.setOrderingStatus(OrderingStatus.RESERVED);
        orderingService.update(ordering);
        return "redirect:/ordering";
    }

    @PostMapping("/{idOrdering}/edit")
    public String OrderEdit(Model model, @RequestParam String date, @PathVariable Long idOrdering) {
        Ordering ordering = orderingService.find(idOrdering);

        Client client = ordering.getClient();
        List<Ordering> orderingList = client.getOrderings();
        for (Ordering i : orderingList) {
            if (i.getId().equals(idOrdering)) continue;
            if (i.getDate().equals(date) && i.getOrderingStatus() == OrderingStatus.NOT_RESERVED) {
                AddAttributesOrdering(model);
                model.addAttribute("message", "Заказ для клиента \"" + client.getFio() + "\" с датой \"" + date + "\" в оформлении");
                return "ordering";
            }
        }

        ordering.setDate(date);
        orderingService.update(ordering);
        return "redirect:/ordering";
    }

    @GetMapping("/{idOrder}/delete")
    public String OrderDelete(@PathVariable Long idOrder) {
        Ordering ordering = orderingService.find(idOrder);
        Client client = ordering.getClient();
        client.removeOrdering(ordering);
        clientService.update(client);
        return "redirect:/ordering";
    }

    @GetMapping("/payment/{idOrder}/shipment")
    public String PaymentOrderShipment(@PathVariable Long idOrder) {
        Ordering ordering = orderingService.find(idOrder);
        ordering.setOrderingStatus(OrderingStatus.SHIPMENT);
        orderingService.update(ordering);
        return "redirect:/ordering";
    }

    @PostMapping("/payment/{id}/edit")
    public String PaymentEdit(@PathVariable Long id, @RequestParam PaymentType paymentType) {
        Ordering ordering = orderingService.find(id);
        ordering.setPaymentType(paymentType);
        orderingService.update(ordering);
        return "redirect:/ordering";
    }

    @GetMapping("/payment/{idOrder}/delete")
    public String PaymentOrderDelete(@PathVariable Long idOrder) {
        DeleteOrderingAndOrderingDetailsAndStatProduct(idOrder);
        return "redirect:/ordering";
    }

    @GetMapping("/shipment/{idOrder}/shipped")
    public String ShipmentOrderShipped(@PathVariable Long idOrder) {
        Ordering ordering = orderingService.find(idOrder);

        List<OrderingDetail> orderingDetailList = orderingService.find(idOrder).getDetails();

        StatProduct statProduct;
        for (OrderingDetail i : orderingDetailList) {
            statProduct = statProductService.findByIdOrderingDetail(i.getId());
            statProduct.setProductStatus(ProductStatus.SHIPPED);
            statProduct.setDate(DateNow());
            statProductService.update(statProduct);
        }

        ordering.setOrderingStatus(OrderingStatus.SHIPPED);
        orderingService.update(ordering);
        return "redirect:/ordering";
    }

    @GetMapping("/shipment/{idOrder}/delete")
    public String ShipmentOrderDelete(@PathVariable Long idOrder) {
        DeleteOrderingAndOrderingDetailsAndStatProduct(idOrder);
        return "redirect:/ordering";
    }

    @GetMapping("/shipped/{idOrder}/delete")
    public String OrderDeleteShipped(@PathVariable Long idOrder) {
        orderingService.delete(idOrder);
        return "redirect:/ordering";
    }
}
