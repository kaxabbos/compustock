package com.compustock.controller;

import com.compustock.controller.main.Attr;
import com.compustock.model.Users;
import com.compustock.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reg")
public class RegCont extends Attr {

    @GetMapping
    public String reg() {
        return "reg";
    }

    @PostMapping
    public String regUser(Model model, @RequestParam String username, @RequestParam String fio, @RequestParam String password, @RequestParam String passwordRepeat) {
        if (usersService.findAll().isEmpty() || usersService.findAll().size() == 0){
            usersService.add(new Users(username, password, fio, Role.ADMIN, defaultAvatar));
            return "redirect:/login";
        }

        if (usersService.findByUsername(username) != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            MainAttr(model);
            return "reg";
        }

        if (!password.equals(passwordRepeat)) {
            model.addAttribute("message", "Некорректный ввод паролей");
            MainAttr(model);
            return "reg";
        }

        usersService.add(new Users(username, password, fio, Role.EMPLOYEE, defaultAvatar));

        return "redirect:/login";
    }
}
