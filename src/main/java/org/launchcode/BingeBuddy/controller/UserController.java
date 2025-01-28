package org.launchcode.BingeBuddy.controller;


import org.launchcode.BingeBuddy.data.UserRepository;
import org.launchcode.BingeBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<User> registerNewUser(@RequestBody User user) {
        if (user.getUsername() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        User newUser = userRepository.save(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/userdetails")
    public ResponseEntity<User> addUserDetails(@RequestBody User user) {
        if (user.getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User userAddGenre = userOptional.get();
        if (user.getGenre() != null) userAddGenre.setGenre(user.getGenre());
        if (user.getAnotherGenre() != null) userAddGenre.setAnotherGenre(user.getAnotherGenre());


        User updatedUser = userRepository.save(userAddGenre);

        return ResponseEntity.ok(updatedUser);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {

        Optional<User> user = userRepository.findById(userId);

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }


    @PutMapping("update/{userId}")
    public ResponseEntity<User> updateUserDetails(@PathVariable Integer userId,
                                                  @RequestBody User user) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User findUser = existingUser.get();

        if (user.getUsername() != null) findUser.setUsername(user.getUsername());
        if (user.getFirstName() != null) findUser.setFirstName(user.getFirstName());
        if (user.getLastName() != null) findUser.setLastName(user.getLastName());
        if (user.getGenre() != null) findUser.setGenre(user.getGenre());
        if (user.getAnotherGenre() != null) findUser.setAnotherGenre(user.getAnotherGenre());

        User updatedUser = userRepository.save(findUser);

        return ResponseEntity.ok(updatedUser);
    }


    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer userId) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUserByUsername(@RequestParam String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found."));
    }


}

