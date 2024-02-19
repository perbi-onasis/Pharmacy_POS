package com.PharmacyPOs.Pharmacy_POS.user_account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotBlank(message = "First name is required")
    private String firstname;

    @NotBlank(message = "Surname is required")
    private String surname;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number is not valid")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;


    @NotBlank(message = "Pharmacy name is required")
    private String pharmacyName;

    @NotBlank(message = "Password is required")
    @Length(min = 8, message = "Password must be at least 8 characters long")
    private String password;


    @NotBlank(message = "Location is required")
    private String location;


    @NotNull(message = "Pharmacy type is required")
    private PharmacyType pharmacyType;
}
