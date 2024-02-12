package com.example.studentmanagement.repository;


import com.example.studentmanagement.entity.Message;
import com.example.studentmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    List<Message> findAllByFrom_IdAndTo_Id(int from, int to);
    List<Message> findAllByTo_IdAndFrom_Id(int to, int from);

}

