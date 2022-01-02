package swa.eshop.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import swa.eshop.user.model.User;
import swa.eshop.user.service.UserService;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController {

    final Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    // CREATE
    @PostMapping(path = "/", produces = "application/json", consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.log(Level.INFO, "User created:\n" + user.toString());
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    // READ
    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (!user.isPresent()) {
            logger.log(Level.INFO, "User read: No user with said ID found! ID = " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        logger.log(Level.INFO, "User read:\n" + user.get().toString());
        return new ResponseEntity<>(user.get(), HttpStatus.FOUND);
    }

    // READ
    @GetMapping
    public ResponseEntity<Iterable<User>> getUsers() {
        if (!userService.getUsers().iterator().hasNext()) {
            logger.log(Level.INFO, "User read, multiple: No users found!");
        }
        logger.log(Level.INFO, "User read, multiple:\n" + userService.getUsers());
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.FOUND);
    }

    // UPDATE
    @PutMapping(value = "/", produces = "application/json", consumes = "application/json")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Optional<User> optionalUser = userService.getUserById(user.getId());
        if (!optionalUser.isPresent()) {
            logger.log(Level.INFO, "User update: User not found.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        logger.log(Level.INFO, "User update:\n" + optionalUser.get() + "\n=== User updated to values:\n" + user.toString());
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (!user.isPresent()) {
            logger.log(Level.INFO, "User delete: User not found.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        logger.log(Level.INFO, "User delete:\n" + user.get());
        return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.OK);
    }

}
