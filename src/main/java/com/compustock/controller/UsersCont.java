package com.compustock.controller;

import com.compustock.controller.main.Attr;
import com.compustock.model.Users;
import com.compustock.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/userAll")
public class UsersCont extends Attr {

    @GetMapping
    public String Profiles(Model model) {
        AddAttributesProfiles(model);
        return "userAll";
    }

    @PostMapping("/{id}/edit")
    public String ProfilesEdit(@PathVariable long id, @RequestParam Role role) {
        Users user = usersService.find(id);
        user.setRole(role);
        usersService.update(user);
        return "redirect:/userAll";
    }

    @GetMapping("/{id}/delete")
    public String ProfilesDelete(Model model, @PathVariable long id) {
        if (getUser().getId() == id) {
            AddAttributesProfiles(model);
            model.addAttribute("message", "Вы не можете удалить свой профиль");
            return "userAll";
        }

        usersService.delete(id);

        return "redirect:/userAll";
    }
}
