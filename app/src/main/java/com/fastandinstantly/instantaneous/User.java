package com.fastandinstantly.instantaneous;

/**
 * Created by Jacques on 4/2/2015.
 */
public class User {
    private String id;
    private String name;
    private String iconID;
    private String gender;
     private String email;


    public User(String id, String name, String iconID, String gender, String email) {
        this.id = id;
        this.name = name;
        this.iconID = iconID;
        this.gender = gender;
        this.email = email;
    }
    public String getGender() {
        return gender;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconID() {
        return iconID;
    }
    public String getEmail() {
        return email;
    }


}
