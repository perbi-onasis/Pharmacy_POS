package com.PharmacyPOs.Pharmacy_POS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//@ComponentScan("com.PharmacyPOs.Pharmacy_POS.user_account")
@ComponentScan(basePackages = "com.PharmacyPOs.Pharmacy_POS")
@SpringBootApplication

public class PharmacyPosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmacyPosApplication.class, args);
	}

}
