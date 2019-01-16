package com.fastandinstantly.instantaneous;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MainActivity extends FragmentActivity {

    CallbackManager callbackManager;
    TextView tv;
    String mID,mName,mEmail,mImage,mGender;
    TextView tvDesc;

    SharedPreferences mPrefs;
    FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            final String[] email = new String[1];
            final String[] gender = new String[1];
            AccessToken token = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            mID = profile.getId();
            mName = profile.getName();
            mImage = profile.getProfilePictureUri(300,300).toString();



            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            Log.i("LoginActivity", response.toString());

                            try {
                                //Name = object.getString("name");

                                email[0] = object.getString("email");
                                gender[0] = object.getString("gender");
                                mEmail = email[0];
                                mGender = gender[0];
                                tvDesc.setText("Still loading we are now logging you in!");

                                        Server s = new Server();
                                        s.sendUserData(mID,
                                                replace(mName),
                                                email[0],
                                                mImage,
                                                gender[0]);

                                        final App app = (App) getApplicationContext();
                                        app.setCurrentUser(new User(mID, mName, mImage, gender[0], email[0]));
                                        saveUser(new User(mID, mName, mImage, gender[0], email[0]));
                                        tvDesc.setText("Done Loading!");

                                        Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                                        startActivity(intent);
                                        finish();








                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender");
            request.setParameters(parameters);
            request.executeAsync();





        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        mPrefs = getApplicationContext().getSharedPreferences("Login",0);
        tvDesc = (TextView)findViewById(R.id.tvDesc);

        if(retrieveUser() != null){
            Intent intent = new Intent(MainActivity.this,IntroActivity.class);
            startActivity(intent);
            finish();
        }
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        //loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager,callback);
        ImageView i =  (ImageView)findViewById(R.id.imageView) ;
        Picasso.with(getApplicationContext()).load(R.drawable.logo).placeholder(R.drawable.logo).into(i);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

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
    public void saveUser(User user){
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user); // myObject - instance of MyObject
        prefsEditor.putString("LoggedInUser", json);
        prefsEditor.commit();
    }
    public User retrieveUser(){
        Gson gson = new Gson();
        String json = mPrefs.getString("LoggedInUser", "");
        User obj = gson.fromJson(json, User.class);
        return obj;
    }
}
