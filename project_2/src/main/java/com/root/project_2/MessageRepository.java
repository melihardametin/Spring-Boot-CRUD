package com.root.project_2;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.root.project_2.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByReceiver(String username);


    List<Message> findBySender(String username);

    Message findFirstById(long id);

}
