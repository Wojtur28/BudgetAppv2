package com.example.budgetappv2.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<User> getUserById(Long id) {
        try{
            return new ResponseEntity<>(userRepository.findById(id).stream().findFirst()
                    .orElse(null), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error with \"getUserById\"");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<User>> getAllUsers(){
        try{
            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error with \"getAllUsers\"");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<User> getUserByUsername(String username) {
        try{
            return new ResponseEntity<>(userRepository.findByUsername(username), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error with \"getUserByUsername\"");
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<User> addUser(User user) {
        try {
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("createUser exception: "+ user.toString()+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<User> updateUser(User user) {
        try {
            User _user = userRepository.findById(user.getId()).stream()
                    .findFirst()
                    .orElse(null);
            if (_user == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            _user.setUsername(user.getUsername());
            _user.setPassword(user.getPassword());
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } catch (Exception e) {
            log.info("updateUser exception: "+ user.toString()+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<HttpStatus> deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.info("deleteUser exception: "+ id.toString()+e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
