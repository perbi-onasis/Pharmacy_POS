package com.PharmacyPOs.Pharmacy_POS.user_account;
import com.PharmacyPOs.Pharmacy_POS.user_account.User;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import com.PharmacyPOs.Pharmacy_POS.user_account.PwdConfiguration;
//import com.PharmacyPOs.Pharmacy_POS.user_account.UserRepository;


@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final PwdConfiguration pwdConfiguration;

    public UserService(UserRepository userRepository, PwdConfiguration pwdConfiguration) {
        this.userRepository = userRepository;
        this.pwdConfiguration = pwdConfiguration;
    }



//    private PasswordEncoder passwordEncoder;

/*    public class PwdSecurityConfig {
        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }
    }
*/

//     Checks if a user is not already registered then it creates the user in the db
    public User registerUser(String email, String firstname, String password, String surname, String phoneNumber, String location, String pharmacyName, String pharmacyType) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
//            throw new UserAlreadyExistException("A user with the email address " + user.getEmail() + " already exists.");
        }
        String hashPassword = pwdConfiguration.passwordEncoder(password);

        User user = new User(firstname, surname, phoneNumber, email, pharmacyName, hashPassword, location, pharmacyType);
        return userRepository.save(user);
    }



    /* Find Users Method*/
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


}
