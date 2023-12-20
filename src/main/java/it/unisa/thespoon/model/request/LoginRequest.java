package it.unisa.thespoon.model.request;

public class LoginRequest {

    private String Email;
    private String Password;

    public LoginRequest(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }
}
