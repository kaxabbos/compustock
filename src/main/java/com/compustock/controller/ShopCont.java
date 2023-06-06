package com.compustock.controller;

import com.compustock.controller.main.Attr;
import com.compustock.model.Client;
import com.compustock.model.Ordering;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ShopCont extends Attr {

    @GetMapping
    public String Clients(Model model) {
        AddAttributesClients(model);
        return "client";
    }

    @PostMapping("/add")
    public String ClientAdd(@RequestParam String fio, @RequestParam String address, @RequestParam String tel) {
        clientService.add(new Client(fio, address, tel));
        return "redirect:/client";
    }

    @PostMapping("/{id}/edit")
    public String ClientEdit(@RequestParam String fio, @RequestParam String tel, @PathVariable Long id) {
        Client client = clientService.find(id);
        client.setFio(fio);
        client.setTel(tel);
        clientService.update(client);
        return "redirect:/client";
    }

    @GetMapping("/{id}/delete")
    public String ClientDelete(@PathVariable Long id) {
        Client client = clientService.find(id);
        List<Ordering> orderingList = client.getOrderings();
        orderingList.forEach(ordering -> DeleteOrderingAndOrderingDetailsAndStatProduct(ordering.getId()));
        clientService.delete(client);
        return "redirect:/client";
    }
}
