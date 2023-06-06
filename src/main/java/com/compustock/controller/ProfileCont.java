package com.compustock.controller;

import com.compustock.controller.main.Attr;
import com.compustock.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Controller
public class ProfileCont extends Attr {

    @GetMapping("/")
    public String Index1(Model model) {
        AddAttributesIndex(model);
        return "index";
    }

    @GetMapping("/index")
    public String Index2(Model model) {
        AddAttributesIndex(model);
        return "index";
    }

    @PostMapping("/user/edit")
    public String userEdit(Model model, @RequestParam String fio, @RequestParam String passwordOld, @RequestParam String password, @RequestParam String passwordRepeat) {
        Users user = getUser();

        if (!passwordOld.equals(user.getPassword())) {
            model.addAttribute("message", "Некорректный ввод текущего пароля");
            AddAttributesIndex(model);
            return "index";
        }

        if (!password.equals("") && !passwordRepeat.equals("")) {
            if (!password.equals(passwordRepeat)) {
                model.addAttribute("message",
                        "Новые пароли не совпадают");
                AddAttributesIndex(model);
                return "index";
            }
            user.setPassword(password);
        }

        if (!fio.equals("")) user.setFio(fio);

        usersService.update(user);

        return "redirect:/index";
    }

    @PostMapping("/user/edit/avatar")
    public String userAvatarEdit(Model model, @RequestParam MultipartFile avatar) {
        if (avatar != null && !Objects.requireNonNull(avatar.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            String res = "";
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "avatars/" + uuidFile + "_" + avatar.getOriginalFilename();
                    avatar.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (IOException e) {
                model.addAttribute("message", "Не удалось изменить аватарку");
                AddAttributesIndex(model);
                return "index";
            }
            Users user = getUser();
            if (!user.getAvatar().equals(defaultAvatar)) {
                try {
                    Files.delete(Paths.get(uploadImg + "/" + user.getAvatar()));
                } catch (IOException e) {
                    model.addAttribute("message", "Не удалось изменить аватарку");
                    AddAttributesIndex(model);
                    return "index";
                }
            }
            user.setAvatar(res);
            usersService.update(user);
        }
        return "redirect:/index";
    }
}
