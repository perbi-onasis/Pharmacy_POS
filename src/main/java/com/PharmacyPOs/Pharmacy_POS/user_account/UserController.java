package com.PharmacyPOs.Pharmacy_POS.user_account;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
//import com.PharmacyPOs.Pharmacy_POS.user_account.PwdConfiguration;
import com.PharmacyPOs.Pharmacy_POS.user_account.UserService;
import java.lang.String;



@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private final UserService userService;

//    private final PwdConfiguration pwdConfiguration;

    public UserController(UserService userService) {
//        this.pwdConfiguration = pwdConfiguration;
        this.userService = userService;
    }


//    @Autowired
//    private PasswordEncoder passwordEncoder;

//    @PostMapping("/register")
//    public ResponseEntity<User> registerUser(@RequestParam("email") String email , @RequestParam("password") String password, @RequestParam("firstname") String firstname, @RequestParam("surname") String surname,
//                                          @RequestParam("phoneNumber") String phoneNumber, @RequestParam("pharmacyName") String pharmacyName,
//                                          @RequestParam("location") String location, @RequestParam("pharmacyType") String pharmacyType) {
//        User registeredUser = userService.registerUser(email, password, firstname, surname, phoneNumber, location, pharmacyName, pharmacyType);
//        return ResponseEntity.ok(registeredUser);
//    }

    //  Register
    @PostMapping("/register")
    public  ResponseEntity<User> registerUser(@RequestParam("email") String email , @RequestParam("password") String password, @RequestParam("firstname") String firstname, @RequestParam("surname") String surname,
                                          @RequestParam("phoneNumber") String phoneNumber, @RequestParam("pharmacyName") String pharmacyName,
                                          @RequestParam("location") String location, @RequestParam("pharmacyType") String pharmacyType){

        String hashPassword = passwordEncoder(password);

        User registeredUser = userService.registerUser(email, hashPassword, firstname, surname, phoneNumber, location, pharmacyName, pharmacyType);
        return ResponseEntity.ok(registeredUser);
//        return null;

    }

    //  Login
    @PostMapping("/login")
    public ResponseEntity<User> loginUser (@RequestParam("email") String email, @RequestParam("password") String password){
        User registeredUser = userService.findUserByEmail(email);
        if (registeredUser == null){
           return ResponseEntity.notFound().build();
       }

       boolean checkPassword = passwordDecoder(password, registeredUser.getPassword());

        if(checkPassword){

        } else{

        }

        /*if(!userService.passwordEncoder.matches(user.getPassword(), registeredUser.getPassword())){
            return ResponseEntity.status(401).build();
        }*/
        return ResponseEntity.ok(registeredUser);
  }

    /**
     * @param  plainPassword
     * */
    public  String passwordEncoder (String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public boolean passwordDecoder(String enteredPassword, String hashedPassword){
        return BCrypt.checkpw(enteredPassword, hashedPassword);
    }

}
