package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.entity.Message;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.repository.MessageRepository;
import com.example.studentmanagement.security.SpringUser;
import com.example.studentmanagement.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public List<Message> gatCurrentMessages(int from, int to) {
        List<Message> messages = ListUtils.union(messageRepository.findAllByTo_IdAndFrom_Id(to, from), messageRepository.findAllByFrom_IdAndTo_Id(to,from));
        messages.sort(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });
        return messages;
    }


    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }
}
