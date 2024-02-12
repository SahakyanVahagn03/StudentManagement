package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.entity.Role;
import com.example.studentmanagement.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface UserService {
    List<User> allUsersByUserRole(Role role);

    Optional<User> findUserByEmail(String email);

    void save(User user, MultipartFile multipartFile) throws IOException;

    Optional<User> getUserByUserId(int id);

    void update(User user, MultipartFile multipartFile) throws IOException;

    List<User> findStudentsByLessonId(int lesson);

    void delete(int id);
}
