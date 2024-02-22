package com.PharmacyPOs.Pharmacy_POS.user_account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /* Checks if a user is not already registered then it creates the user in the db*/
    public User registerUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new UserAlreadyExistException("A user with the email address " + user.getEmail() + " already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /* Find Users Method*/
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    /* An Exception method for Already Existing Users*/
    public class UserAlreadyExistException extends RuntimeException {
        public UserAlreadyExistException(String message) {
                super(message);
        }
    }
}
