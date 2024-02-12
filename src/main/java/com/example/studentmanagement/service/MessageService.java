package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Message;
import com.example.studentmanagement.entity.User;

import java.util.List;

public interface MessageService {
    List<Message> gatCurrentMessages(int from, int to);

    void save (Message message);
}
