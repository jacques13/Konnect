package com.fastandinstantly.instantaneous;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    SharedPreferences mPrefs ;
    //User LoggedInUser = retrieveUser();
    public AccountHeader headerResult;
    private Drawer result;
    FragmentManager fragmentManager;
    Fragment fragment = null;
    Class fragmentClass = null;
    private List<User> users = new ArrayList<User>();
    private int counter = 0;
    CallbackManager callbackManager;
    ShareDialog shareDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mPrefs = getApplicationContext().getSharedPreferences("Login",0);
        final App app = (App) getApplicationContext();
        app.populateUserList();
        app.setFontsForEntireApp();
        users = app.getUsers();
        app.setUser_id(retrieveUser().getId());
        app.populateInfoList();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                DrawerImageLoader.init(new AbstractDrawerImageLoader() {
                    @Override
                    public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                        Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
                    }
                    @Override
                    public void cancel(ImageView imageView) {
                        Picasso.with(imageView.getContext()).cancelRequest(imageView);
                    }
                });


        headerResult = new AccountHeaderBuilder()
                        .withActivity(this)
                        .withCompactStyle(false)
                        .withHeaderBackground(R.drawable.header)
                         .withSavedInstance(savedInstanceState).withHeightDp(150)
                        .addProfiles(

                                new ProfileDrawerItem().withName(retrieveUser().getName()).withEmail(retrieveUser().getEmail())
                                        .withIcon(retrieveUser().getIconID())

                        )
                        .withSelectionListEnabledForSingleProfile(false)
                        .build();

                result = new DrawerBuilder()
                        .withActivity(this)
                        .withAccountHeader(headerResult)
                        .withToolbar(toolbar)
                        .withFullscreen(true)

                        .addDrawerItems(
                                new SectionDrawerItem().withName("Features"),
                                new PrimaryDrawerItem().withName("Users").withIdentifier(0),
                                new PrimaryDrawerItem().withName("Female").withIdentifier(1),
                                new PrimaryDrawerItem().withName("Male").withIdentifier(2),
                                new PrimaryDrawerItem().withName("Search").withIdentifier(3),
                                new PrimaryDrawerItem().withName("Random").withIdentifier(4),
                                new SectionDrawerItem().withName("Profile"),
                                new PrimaryDrawerItem().withName("My Profile").withIdentifier(5),
                                new PrimaryDrawerItem().withName("Update details").withIdentifier(6),
                                new SectionDrawerItem().withName("Help Us"),
                                new PrimaryDrawerItem().withName("Go to Our Facebook Page").withIdentifier(7),
                                new PrimaryDrawerItem().withName("Share our App on your Facebook profile").withIdentifier(8)
                              /*  new SectionDrawerItem().withName("Other apps"),
                                 new SecondaryDrawerItem().withName("Other apps")*/

                        )
                         .withSavedInstance(savedInstanceState)
                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                switch (position) {
                                    case 2:
                                        fragmentClass = UsersFragment.class;
                                        break;
                                    case 3:
                                        fragmentClass = FemaleFragment.class;
                                        break;
                                    case 4:
                                        fragmentClass = MaleFragment.class;
                                        break;
                                    case 5:
                                        fragmentClass = SearchFragment.class;
                                        break;
                                    case 6:
                                        fragmentClass = RandomFragment.class;
                                        break;

                                    case 8:
                                        app.setUser_id(retrieveUser().getId());
                                        gotoProfile();
                                        break;
                                    case 9:
                                        fragmentClass = UpdateDetailsFragment.class;
                                        app.setUser_id(retrieveUser().getId());
                                        break;
                                    case 11:
                                        openPage("https://www.facebook.com/GetShare-Social-media-Info-604936769713525/");
                                        break;
                                    case 12:
                                        if (ShareDialog.canShow(ShareLinkContent.class)) {
                                            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                                    .setContentTitle("Get and Share Social media Info")
                                                    .setContentDescription(
                                                            "Get all my Social media info with this ID:"+retrieveUser().getId())
                                                    .setContentUrl(Uri.parse("https://www.facebook.com/GetShare-Social-media-Info-604936769713525/"))
                                                    .build();

                                            shareDialog.show(linkContent);
                                        }
                                        break;
                                }
                                try {
                                    fragment = (Fragment) fragmentClass.newInstance();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                fragmentManager = getSupportFragmentManager();
                                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragmentLayout, fragment).commit();
                                return false;
                            }
                        })
                        .build();

        fragmentClass = UsersFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragmentLayout, fragment).commit();



    }

    public User retrieveUser(){
        Gson gson = new Gson();
        String json = mPrefs.getString("LoggedInUser", "");
        User obj = gson.fromJson(json, User.class);
        return obj;

    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void gotoProfile(){

        fragmentClass = ProfileFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragmentLayout, fragment).commit();
    }

    public void setUserID(String id) {
        final App app = (App) getApplicationContext();
        app.setUser_id(id);

    }
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }
    public void openPage(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
