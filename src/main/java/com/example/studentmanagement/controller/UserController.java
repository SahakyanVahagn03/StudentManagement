package com.example.studentmanagement.controller;


import com.example.studentmanagement.entity.Role;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping(value = "/users/{role}")
    public String getUsersByUserRole(@PathVariable("role") String role, ModelMap modelMap) {
        if (Role.STUDENT.name().equals(role.toUpperCase())) {
            modelMap.put("users", userService.allUsersByUserRole(Role.STUDENT));
            return "users";
        }
        if (Role.TEACHER.name().equals(role.toUpperCase())) {
            modelMap.put("users", userService.allUsersByUserRole(Role.TEACHER));
            return "users";
        }
        return "redirect:/";
    }


    @GetMapping("/users/register")
    public String openAddTeacherPage() {
        return "register";
    }

    @PostMapping("/users/register")
    public String addTeacher(@RequestParam("name") String name,
                             @RequestParam("surname") String surname,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam(value = "role", required = false) Role role,
                             @RequestParam("picture")
                             MultipartFile multipartFile) {

        if (userService.findUserByEmail(email).isEmpty()) {
            return "redirect:/users/register";
        }
        try {
            userService.save(new User(0, name, surname, email, null, password, null, role), multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/loginPage";
    }


    @GetMapping("/users/update/{id}")
    public String userEditPage(@PathVariable("id") int id, ModelMap modelMap) {
        if (userService.getUserByUserId(id).isEmpty()) {
            return "redirect:/";
        }
        modelMap.put("user", userService.getUserByUserId(id).get());
        return "userUpdate";
    }

    @PostMapping("/users/update")
    public String userEdit(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("picName")
            MultipartFile multipartFile) {
        if (userService.getUserByUserId(id).isPresent() && userService.findUserByEmail(email).isPresent()) {
            try {
                userService.update(new User(id, name, surname, email, null, password, null, null), multipartFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "redirect:/";
    }

    @GetMapping("/users/delete/{id}")
    public String userDelete(@PathVariable("id") int id) {
       userService.delete(id);
       return "redirect:/";
    }
}
