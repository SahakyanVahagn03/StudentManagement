package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface LessonService {
    Optional<Lesson> getLessonByLessonId(int id);

    void chooseLesson(int id, User user) throws IOException;

    void deleteLessonByLessonIdAndUserID(int userId,int lessonId);

    List<Lesson> findAllLessons();

    void save(Lesson lesson);

    void lessonUpdate(Lesson lesson);
}
