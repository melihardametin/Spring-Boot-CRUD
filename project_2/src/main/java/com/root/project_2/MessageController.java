package com.root.project_2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
@RequestMapping("/")
public class MessageController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;



    @DeleteMapping({"/adminuser", "/user"})
    public ResponseEntity<Message> deleteMessage(@RequestBody Message message, @RequestHeader("Authorization") String token) {
        token = token.substring(7);

        Optional<Message> messageData = messageRepository.findById(message.getId());

        try {

            // Perform token validation logic here
            User user = userRepository.findFirstByToken(token);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            if (messageData.isPresent()) {
                Message _message = messageData.get();
                _message.setDeleted();
                return new ResponseEntity<>(messageRepository.save(_message), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @PostMapping({"/user/outbox", "/adminuser/outbox"})
    public ResponseEntity<List<Message>> postOutbox(@RequestHeader("Authorization") String token) {
        token = token.substring(7);

        try {
            // Perform token validation logic here
            User curr_user = userRepository.findFirstByToken(token);
            if (curr_user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            User u = userRepository.findFirstByToken(token);
            String username = u.getUsername();
            List<Message> messages = new ArrayList<Message>();
            messageRepository.findBySender(username).forEach(messages::add);
            //ist<Message> messages = messageRepository.findBySender(username);

            if (messages.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping({"/user/send","/adminuser/send"})
    public ResponseEntity<Message> sendMessage(@RequestBody Message message, @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        try {
            // Perform token validation logic here
            User curr_user = userRepository.findFirstByToken(token);
            if (curr_user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            User to_user = userRepository.findFirstByUsername(message.getReceiver());
            if (to_user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Message _message = new Message(message.getSender(), message.getReceiver(), message.getTitle(),message.getMessage());
            messageRepository.save(_message);
            return new ResponseEntity<>(_message, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = {"/user","/adminuser"})
    public ResponseEntity<List<Message>> getInbox(@RequestHeader("Authorization") String token) {
        token = token.substring(7);

        try {
            // Perform token validation logic here
            User curr_user = userRepository.findFirstByToken(token);
            if (curr_user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            User u = userRepository.findFirstByToken(token);
            String username = u.getUsername();
            //List<Message> messages = new ArrayList<Message>();
            List<Message> messages = messageRepository.findByReceiver(username);

            if (messages.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}