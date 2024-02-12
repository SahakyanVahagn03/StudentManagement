package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Message;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.security.SpringUser;
import com.example.studentmanagement.service.MessageService;
import com.example.studentmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @GetMapping("/my/lesson/message/{id}")
    public String MessagePage(@PathVariable("id") int id, @AuthenticationPrincipal SpringUser springUser, ModelMap modelMap) {
        modelMap.put("user",userService.getUserByUserId(id).get());
        modelMap.put("messages", messageService.gatCurrentMessages(springUser.getUser().getId(), id));
        return "chatMessage";
    }


    @PostMapping("/my/lesson/message")
    public String sendMessage(@RequestParam("to") int to_id,@RequestParam("message") String message, @AuthenticationPrincipal SpringUser springUser) {
        messageService.save(new Message(0,message,springUser.getUser(),userService.getUserByUserId(to_id).get(),new Date()));
        return "redirect:/my/lesson/message/" + to_id;
    }
}
