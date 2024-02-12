package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.entity.Role;
import com.example.studentmanagement.repository.LessonRepository;
import com.example.studentmanagement.security.SpringUser;
import com.example.studentmanagement.service.LessonService;
import com.example.studentmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final UserService userService;

    @GetMapping(value = "/lessons")
    public String getLessons(ModelMap modelMap) {
        List<Lesson> lessons = lessonService.findAllLessons();
        modelMap.addAttribute("lessons", lessons);
        return "lessons";
    }

    @GetMapping(value = "/lessons/add")
    public String openAddStudentPage(ModelMap modelMap) {
        modelMap.put("teachers", userService.allUsersByUserRole(Role.TEACHER));
        return "addLesson";
    }

    @PostMapping(value = "/lessons/add")
    public String addLesson(@ModelAttribute Lesson lesson, @AuthenticationPrincipal SpringUser springUser) {
        if (springUser.getUser().getRole() == Role.STUDENT) {
            return "redirect:/";
        } else if (springUser.getUser().getRole() == Role.TEACHER) {
            lesson.setTeacher(springUser.getUser());
        }
        lessonService.save(lesson);
        return "redirect:/lessons";
    }
    @GetMapping("/myLesson/{id}")
    public String myLesson(@PathVariable("id") int id, @AuthenticationPrincipal SpringUser springUser,ModelMap modelMap) {
        try {
            Optional<Lesson> lessonByLessonId = lessonService.getLessonByLessonId(id);
            if (lessonByLessonId.isEmpty()){
                return "redirect:/";
            }
            modelMap.put("lesson", lessonByLessonId.get());
            modelMap.put("users", userService.findStudentsByLessonId(id));
            lessonService.chooseLesson(id, springUser.getUser());
        } catch (IOException e) {
            return "redirect:/lessons";
        }
        return "myLesson";
    }

    @GetMapping("/lessons/choose/{id}")
    public String chooseLesson(@PathVariable("id") int id, @AuthenticationPrincipal SpringUser springUser,ModelMap modelMap) {
        try {
            lessonService.chooseLesson(id, springUser.getUser());
        } catch (IOException e) {
            return "redirect:/lessons";
        }
        return "redirect:/myLesson/"+id;
    }
    @GetMapping("/lessons/delete/{lessonId}")
    public String deleteLesson(@PathVariable("lessonId") int lessonId, @AuthenticationPrincipal SpringUser springUser) {
        lessonService.deleteLessonByLessonIdAndUserID(springUser.getUser().getId(), lessonId);
        return "redirect:/lessons";
    }
    @GetMapping("/lessons/update/{lessonId}")
    public String lessonUpdate(@PathVariable("lessonId") int lessonId, ModelMap modelMap) {
        modelMap.put("lesson", lessonService.getLessonByLessonId(lessonId).get());
        return "lessonUpdate";
    }
    @PostMapping("/lessons/update")
    public String lessonUpdate(@ModelAttribute Lesson lesson) {
        lessonService.lessonUpdate(lesson);
        return "redirect:/lessons";
    }

}


