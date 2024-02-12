package com.example.studentmanagement.service.impl;


import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.repository.LessonRepository;
import com.example.studentmanagement.repository.UserRepository;
import com.example.studentmanagement.security.SpringUser;
import com.example.studentmanagement.service.LessonService;
import com.example.studentmanagement.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Lesson> getLessonByLessonId(int id) {
        return lessonRepository.findById(id);
    }

    @Override
    public void chooseLesson(int id, User user) {
        Optional<Lesson> lessonByLessonId = getLessonByLessonId(id);
        lessonByLessonId.ifPresent(user::setLesson);
        userRepository.save(user);
    }

    @Override
    public void deleteLessonByLessonIdAndUserID(int userId, int lessonId) {
        List<User> allByLessonId = userRepository.findAllByLessonId(lessonId);
        for (User user : allByLessonId) {
            user.setLesson(null);
        }
        userRepository.saveAll(allByLessonId);
        lessonRepository.removeLessonById(lessonId);
    }

    @Override
    public List<Lesson> findAllLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public void save(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public void lessonUpdate(Lesson lesson) {
        lesson.setTeacher(lessonRepository.findById(lesson.getId()).get().getTeacher());
        lessonRepository.save(lesson);
    }
}
