package com.example.drawer1;

public class User {
    String id;
    String Name;
    String phone;
    String Email;
    String Password;

    User(){

    }

    public User(String id, String name, String phone, String email, String password) {
        this.id = id;
        Name = name;
        this.phone = phone;
        Email = email;
        Password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }
}
