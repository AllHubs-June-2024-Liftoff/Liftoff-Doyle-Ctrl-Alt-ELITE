package org.launchcode.BingeBuddy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {
    private String username;
    private String firstName;
    private String lastName;
    private String genre;
    private String genre2;



    public User(Integer id, String username) {
        this();
        this.username = username;

    }

    public User() {
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getGenre() {return genre;}

    public void setGenre(String genre) {this.genre = genre;}

    public String getGenre2() {return genre2;}

    public void setGenre2(String genre2) {this.genre2 = genre2;}


}
