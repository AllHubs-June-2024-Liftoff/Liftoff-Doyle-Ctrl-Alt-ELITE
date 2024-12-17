package org.launchcode.BingeBuddy.models;

public class User extends AbstractEntity {
    private String name;
    private String email;

    public User(Long id, String name, String email) {
        this();
        this.name = name;
        this.email = email;
    }

    public User() {
    }
    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
