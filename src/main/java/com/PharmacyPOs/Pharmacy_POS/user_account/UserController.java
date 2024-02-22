package com.PharmacyPOs.Pharmacy_POS.user_account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ReponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser (@RequestBody User user){
        User registeredUser = userService.findUserByEmail(user.getEmail());
        if (registeredUser == null){
            return ResponseEntity.notFound().build();
        }
        if(!userService.passwordEncoder.matches(user.getPassword(), registeredUser.getPassword())){
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(registeredUser);
    }

}
