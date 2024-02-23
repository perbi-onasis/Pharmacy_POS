package com.PharmacyPOs.Pharmacy_POS.user_account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@SuppressWarnings("deprecation")
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @SuppressWarnings("deprecation")
    @NotBlank(message = "First name is required")
    private String firstname;

    // @SuppressWarnings("deprecation")
    @NotBlank(message = "Surname is required")
    private String surname;

    // @SuppressWarnings("deprecation")
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number is not valid")
    private String phoneNumber;

    // @SuppressWarnings("deprecation")
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    // @SuppressWarnings("deprecation")
    @NotBlank(message = "Pharmacy name is required")
    private String pharmacyName;
    
    // @SuppressWarnings("deprecation")
    @NotBlank(message = "Password is required")
    @Length(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    // @SuppressWarnings("deprecation")
    @NotBlank(message = "Location is required")
    private String location;


    @NotNull(message = "Pharmacy type is required")
    private PharmacyType pharmacyType;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
