package com.example.studentmanagement.repository;
import com.example.studentmanagement.entity.Role;
import com.example.studentmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findAllByRole(Role role);
    List<User> findAllByLessonId(int lessonId);


    Optional<User> findUserByEmail(String email);





}
