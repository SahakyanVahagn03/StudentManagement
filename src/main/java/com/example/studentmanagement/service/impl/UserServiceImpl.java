package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.entity.Role;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.repository.UserRepository;
import com.example.studentmanagement.service.UserService;
import com.example.studentmanagement.util.PictureUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${file.upload.directory}")
    private String uploadDirectory;
    @Override
    public List<User> allUsersByUserRole(Role role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public Optional<User> findUserByEmail(String name) {
        return userRepository.findUserByEmail(name);
    }


    @Override
    public void save(User user, MultipartFile multipartFile) throws IOException {
        user.setPicName(uploadPicture(multipartFile));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.STUDENT);
        }
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUserId(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void update(User user, MultipartFile multipartFile) throws IOException {
        String picName = uploadPicture(multipartFile);
        Optional<User> byId = userRepository.findById(user.getId());
        if (picName != null) {
            user.setPicName(picName);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPicName(byId.get().getPicName());
        }
        user.setLesson(byId.get().getLesson());
        user.setRole(byId.get().getRole());
        userRepository.save(user);
    }

    @Override
    public List<User> findStudentsByLessonId(int lesson) {
        return userRepository.findAllByLessonId(lesson);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }


    public String uploadPicture(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.getOriginalFilename().isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            return picName;
        }
        return null;
    }
}

