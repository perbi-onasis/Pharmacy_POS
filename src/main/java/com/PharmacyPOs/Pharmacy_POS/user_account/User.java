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
    private String pharmacyType;

    public User(){

    }

    public User(String firstname, String surname, String phoneNumber, String email, String pharmacyName, String password, String location, String pharmacyType) {
        this.firstname = firstname;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.pharmacyName = pharmacyName;
        this.password = password;
        this.location = location;
        this.pharmacyType = pharmacyType;
    }

    public String getId() {
        return id;
    }

    public String setId(String id) {
        return this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String setFirstname(String firstname) {
       return this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String setSurname(String surname) {
       return this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String setPhoneNumber(String phoneNumber) {
       return this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String setEmail(String email) {
       return this.email = email;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public String setPharmacyName(String pharmacyName) {
       return this.pharmacyName = pharmacyName;
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
       return this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public String setLocation(String location) {
       return this.location = location;
    }

    public String getPharmacyType() {
        return pharmacyType;
    }

    public String setPharmacyType(String pharmacyType) {
       return this.pharmacyType = pharmacyType;
    }
}
