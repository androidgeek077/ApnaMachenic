package com.example.splashscreen.Models;


public class CustomerSignUpModel {

    String username;
    String email;
    String password;
    String edtConfirmTxtPassword;
    String number;
    String guardianContactNumber;


    public CustomerSignUpModel(String username, String email, String password, String number, String guardianContactNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.number = number;
        this.guardianContactNumber = guardianContactNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getEdtConfirmTxtPassword() {
        return edtConfirmTxtPassword;
    }

    public void setEdtConfirmTxtPassword(String edtConfirmTxtPassword) {
        this.edtConfirmTxtPassword = edtConfirmTxtPassword;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGuardianContactNumber() {
        return guardianContactNumber;
    }

    public void setGuardianContactNumber(String guardianContactNumber) {
        this.guardianContactNumber = guardianContactNumber;
    }
}
