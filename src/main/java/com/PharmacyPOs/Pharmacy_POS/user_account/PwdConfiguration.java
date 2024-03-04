//package com.PharmacyPOs.Pharmacy_POS.user_account;
//
//import org.mindrot.jbcrypt.BCrypt;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Service;
//@Configuration
//public class PwdConfiguration {
//
//    private final UserService userService;
//    public PwdConfiguration(UserService userService) {
//        this.userService = userService;
//    }
//
//    public  String passwordEncoder (String plainPassword){
//        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
//    }
//
//    public boolean passwordDecoder(String enteredPassword, String hashedPassword){
//        return BCrypt.checkpw(enteredPassword, hashedPassword);
//    }
//
////    @Bean
////    public UserService userService() {
////        return new UserService(userRepository(), passwordEncoder());
////    }
//
//
///*    @Bean
//    public UserRepository userRepository() {
//        return new UserRepositoryImpl(); // replace with your actual UserRepository implementation
//    }
//*/
//
//}
