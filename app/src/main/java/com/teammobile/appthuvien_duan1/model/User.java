package com.teammobile.appthuvien_duan1.model;

public class User {
    private String ma;
    private String email;
    private String username;
    private String password;
    private int role;
    private int isActive;

    public User(String ma, String email, String username, String password, int role, int isActive) {
        this.ma = ma;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    public User() {
    }

    public User(String email, String username, String password, int role, int isActive) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    public String getMa() {
        return ma;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }

    public int getIsActive() {
        return isActive;
    }
}
