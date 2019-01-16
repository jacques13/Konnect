package com.fastandinstantly.instantaneous;

import android.app.Application;
import android.util.Log;

import com.facebook.AccessToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by User on 2016/12/03.
 */

public class App extends Application {

    private List<User> users = new ArrayList<User>();
    private List<User> females = new ArrayList<User>();
    private List<User> males = new ArrayList<User>();
    private String[] socialMedia = {"facebook","instagram","snapchat"
            ,"tumblr","twitter","linkedin",
            "pinterst","reddit","wechat","whatsapp",
            "youtube","askfm","flickr","google"};



    private List<User> randoms = new ArrayList<User>();

    public List<User> getInfoList() {
        return infoList;
    }

    private List<User> infoList = new ArrayList<User>();
    public String user_id = null;

    public User currentUser;
    public boolean loggedIn;



    public String[] info  = new String[15];

    public void setFontsForEntireApp(){
        FontsOverride.setDefaultFont(this, "DEFAULT", "LibreBaskerville-Regular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "LibreBaskerville-Regular.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "LibreBaskerville-Regular.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "LibreBaskerville-Regular.ttf");
    }

    public List<User> getFemales() {
        return females;
    }

    public List<User> getMales() {
        return males;
    }

    public List<User> getRandoms() {
        return randoms;
    }
    public void populateInfoList(){
        Thread t = new Thread() {
            public void run() {
                Server s = new Server();
                s.getInfo(user_id);
                info = s.info;
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    public String[] getInfo() {
        return info;
    }
    public void populateUserList(){
        new Thread(){
            public void run(){
                Server m = new Server();
                m.getUsers();
                users = m.getUsersList();
            }

        }.start();
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String ID) {

        this.user_id = ID;
    }

    public List<User> getUsers() {
        return users;
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {

        this.currentUser = currentUser;
    }
        public boolean isLoggedIn() {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            return accessToken != null;
        }


    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void getInfoOfUser(final String ID){

        new Thread() {
            public void run() {
                Server s = new Server();
                s.getInfo(ID);
                info = s.info;
            }
        }.start();

    }
    public void generateFemales(){
        for (User data :   users) {
           if(data.getGender().equals("female")){
               females.add(data);
           }
        }
    }
    public void generateMales(){
        for (User data :   users) {
            if(data.getGender().equals("male")){
                males.add(data);
            }
        }
    }
    public void randomUsers(){
        Random rand = new Random();
        int  one = rand.nextInt(users.size());
        int  two = rand.nextInt(users.size());
        int  three = rand.nextInt(users.size());
        int  four = rand.nextInt(users.size());

        randoms.add(users.get(one));
        randoms.add(users.get(two));
        randoms.add(users.get(three));
        randoms.add(users.get(four));
    }
    public User getUserForProfile(){
        for (User data :   users) {
            if(data.getId().equals(user_id)){
              return  data;
            }
        }
        return null;
    }

}
