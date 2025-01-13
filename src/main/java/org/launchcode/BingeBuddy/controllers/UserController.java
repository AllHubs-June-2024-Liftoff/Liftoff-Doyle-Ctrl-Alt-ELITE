package org.launchcode.BingeBuddy.controllers;


import org.launchcode.BingeBuddy.data.UserRepository;
import org.launchcode.BingeBuddy.models.Login;
import org.launchcode.BingeBuddy.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;



    @GetMapping("/add")
    public ResponseEntity<User> addUser(@RequestParam("email") String email, @RequestParam("username") String user) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(user);


        userRepository.save(newUser);

        return ResponseEntity.ok(newUser);
    }


    @PostMapping("/add")
    public ResponseEntity<User> processNewUser(@PathVariable("userId") Integer userId) {
        User user = new User();
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/userdetails")
    public User addUserDetails(@RequestParam("userId") Integer userId) {

        return userRepository.findById(userId).get();
    }

    @PostMapping("/userdetails")
    public ResponseEntity<User> handleUserDetails(@RequestParam("email") String email, @RequestParam(value = "username", required = false) String username, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("genre") String genre, @RequestParam(value = "anotherGenre", required = false) String anotherGenre) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setGenre(genre);
        user.setAnotherGenre(anotherGenre);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
