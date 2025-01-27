package org.launchcode.BingeBuddy.controller;

import org.launchcode.BingeBuddy.data.UserRepository;
import org.launchcode.BingeBuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestParam String email,
                                             @RequestParam String username,
                                             @RequestParam(required = false) String firstName,
                                             @RequestParam(required = false) String lastName) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);

        userRepository.save(newUser);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List<User>> getAllUsers(@PathVariable String searchTerm) {
        List<User> iResults = userRepository.findAll();
        ArrayList<User> results = new ArrayList<>();
        for (int i = 0; i < iResults.size(); i++) {
            if (iResults.get(i).getUsername() != null && iResults.get(i).getUsername().contains(searchTerm)) {
                results.add(iResults.get(i));
            }
        }
        return ResponseEntity.ok(results);
    }


    @PostMapping("/add-test-user")
    public ResponseEntity<String> addTestUser() {
        Optional<User> existingUser = userRepository.findById(1);
        if (existingUser.isEmpty()) {
            User testUser = new User();
            testUser.setEmail("test@example.com");
            testUser.setUsername("testuser");
            testUser.setFirstName("Test");
            testUser.setLastName("User");
            //testUser.setGenre("Action");
           // testUser.setAnotherGenre("Comedy");

            userRepository.save(testUser);
            return ResponseEntity.ok("Test user added with ID " + testUser.getId());
        } else {
            return ResponseEntity.ok("Test user already exists at ID 1");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserDetails(@PathVariable Integer userId,
                                                  @RequestParam(required = false) String email,
                                                  @RequestParam(required = false) String username,
                                                  @RequestParam(required = false) String firstName,
                                                  @RequestParam(required = false) String lastName,
                                                  @RequestParam(required = false) String genre,
                                                  @RequestParam(required = false) String anotherGenre) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (email != null) user.setEmail(email);
            if (username != null) user.setUsername(username);
            if (firstName != null) user.setFirstName(firstName);
            if (lastName != null) user.setLastName(lastName);
            //if (genre != null) user.setGenre(genre);
            //if (anotherGenre != null) user.setAnotherGenre(anotherGenre);

            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

