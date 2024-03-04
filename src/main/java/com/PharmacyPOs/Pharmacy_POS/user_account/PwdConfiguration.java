package com.PharmacyPOs.Pharmacy_POS.user_account;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
@Configuration
public class PwdConfiguration {
    @Bean

    public  String passwordEncoder (String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public boolean passwordDecoder(String enteredPassword, String hashedPassword){
        return BCrypt.checkpw(enteredPassword, hashedPassword);
    }
}
