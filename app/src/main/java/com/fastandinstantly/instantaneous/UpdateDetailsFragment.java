package com.fastandinstantly.instantaneous;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import mehdi.sakout.fancybuttons.FancyButton;

import static com.facebook.FacebookSdk.getApplicationContext;


public class UpdateDetailsFragment extends Fragment {
    SharedPreferences mPrefs ;
    public String[] info  = new String[15];
    private String[] socialMedia = {"facebook","instagram","snapchat"
            ,"tumblr","twitter","linkedin",
            "pinterst","reddit","wechat","whatsapp",
            "youtube","askfm","flickr","google"};

    EditText etFacebook ;
    EditText etInstagram ;

    EditText etSnapchat ;

    EditText etTumblr;

    EditText etTwitter ;

    EditText etLinkedin ;

    EditText etPinterest ;

    EditText etReddit;
    EditText etWechat;

    EditText etWhatsapp ;

    EditText etYoutube ;

    EditText etAskfm ;

    EditText etFlickr;

    EditText etGoogle  ;
    public UpdateDetailsFragment() {
        // Required empty public constructor
    }


    public static UpdateDetailsFragment newInstance(String param1, String param2) {
        UpdateDetailsFragment fragment = new UpdateDetailsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = getApplicationContext().getSharedPreferences("Login",0);
        final App app = (App) getApplicationContext();
        app.populateInfoList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final App app = (App) getApplicationContext();
        View v =  inflater.inflate(R.layout.fragment_update_details, container, false);
        FancyButton  btnSubmit = (FancyButton)v.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNewUserData();
            }
        });
        app.populateInfoList();
        String[] r = app.getInfo();


        etFacebook = (EditText)v.findViewById(R.id.facebookText);
        etFacebook.setText(r[0]);

        etInstagram = (EditText)v.findViewById(R.id.intagramText);
        etInstagram.setText(r[1]);

        etSnapchat = (EditText)v.findViewById(R.id.snapchatText);
        etSnapchat.setText(r[2]);

        etTumblr = (EditText)v.findViewById(R.id.tumblrText);
        etTumblr.setText(r[3]);

        etTwitter = (EditText)v.findViewById(R.id.twitterText);
        etTwitter.setText(r[4]);

        etLinkedin = (EditText)v.findViewById(R.id.linkedinText);
        etLinkedin.setText(r[5]);

        etPinterest = (EditText)v.findViewById(R.id.pinterestText);
        etPinterest.setText(r[6]);

        etReddit = (EditText)v.findViewById(R.id.redditText);
        etReddit.setText(r[7]);

        etWechat = (EditText)v.findViewById(R.id.wechatText);
        etWechat.setText(r[8]);

        etWhatsapp = (EditText)v.findViewById(R.id.whatsappText);
        etWhatsapp.setText(r[9]);

        etYoutube = (EditText)v.findViewById(R.id.youtubeText);
        etYoutube.setText(r[10]);

        etAskfm = (EditText)v.findViewById(R.id.askfmText);
        etAskfm.setText(r[11]);

        etFlickr = (EditText)v.findViewById(R.id.flickrText);
        etFlickr.setText(r[12]);

        etGoogle = (EditText)v.findViewById(R.id.googleText);
        etGoogle.setText(r[13]);

        return v;
    }


    public User retrieveUser(){
        Gson gson = new Gson();
        String json = mPrefs.getString("LoggedInUser", "");
        User obj = gson.fromJson(json, User.class);
        return obj;

    }
    public void sendNewUserData(){
        final String facebook = etFacebook.getText().toString();

        final String instagram = replace(etInstagram.getText().toString()).replaceAll("[^A-Za-z0-9]", "");

        final String snapchat = replace(etSnapchat.getText().toString()).replaceAll("[^A-Za-z0-9]", "");

        final String tumblr = replace(etTumblr.getText().toString().replace(".tumblr.com", "")).replaceAll("[^A-Za-z0-9]", "");

        final String twitter = replace(etTwitter.getText().toString()).replaceAll("[^A-Za-z0-9]", "");

        final String linkedin = replace(etLinkedin.getText().toString()).replaceAll("[^A-Za-z0-9]", "");

        final String pinterest = replace(etPinterest.getText().toString()).replaceAll("[^A-Za-z0-9]", "");

        final String reddit = replace(etReddit.getText().toString()).replaceAll("[^A-Za-z0-9]", "");

        final String wechat =  replace(etWechat.getText().toString()).replaceAll("[^A-Za-z0-9]", "");

        final String whatsapp =   replace(etWhatsapp.getText().toString()).replaceAll("[^A-Za-z0-9]", "");

        final String youtube =  replace(etYoutube.getText().toString()).replaceAll("[^A-Za-z0-9]", "");

        final String askfm =   replace(etAskfm.getText().toString()).replaceAll("[^A-Za-z0-9]", "");

        final String flickr =   replace( etFlickr.getText().toString()).replaceAll("[^A-Za-z0-9]", "");

        final String google =   replace( etGoogle.getText().toString()).replaceAll("[^A-Za-z0-9]", "");

        new Thread(){
            public void run(){
                new Server().sendUserInfo(retrieveUser().getId(),replace(facebook),instagram,snapchat,tumblr,twitter,linkedin,pinterest,reddit,wechat,whatsapp,youtube,askfm,flickr,google);
                ((IntroActivity)getActivity()).gotoProfile();
            }

        }.start();


    }

    public String replace(String str) {
        String[] words = str.split(" ");
        StringBuilder sentence = new StringBuilder(words[0]);

        for (int i = 1; i < words.length; ++i) {
            sentence.append("%20");
            sentence.append(words[i]);
        }

        return sentence.toString();
    }
}
