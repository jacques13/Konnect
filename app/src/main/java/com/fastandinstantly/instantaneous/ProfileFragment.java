package com.fastandinstantly.instantaneous;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.ExtendableListView;
import com.etsy.android.grid.StaggeredGridView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ProfileFragment extends Fragment {
   private boolean mHasRequestedMore;
    private AdapterProfile mAdapter;
    private List<User> mData = new ArrayList<User>();
   private GridView mListView ;
    final App app = (App) getApplicationContext();
    public String[] info  = new String[15];
    private String[] socialMedia = {"facebook","instagram","snapchat"
            ,"tumblr","twitter","linkedin",
            "pinterest","reddit","wechat","whatsapp",
            "youtube","askfm","flickr","google"};
    SharedPreferences mPrefs ;
    String[] r;



    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = getApplicationContext().getSharedPreferences("Login",0);
        //mAdapter.clear();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = null;
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        app.populateInfoList();
        mData.add(app.getUserForProfile());
        mData.add(new User("IDFORSHARE","ID : "+app.getUserForProfile().getId(),"http://10.0.0.4/myphp/joiner.com/app/images/logo.png",
                "Get/Share Social info",""));
        String[] r = app.getInfo();


//        mAdapter.clear();



        mListView = (GridView) view.findViewById(android.R.id.list);

        mListView.invalidateViews();

        mListView.setLayoutParams( new LinearLayout.LayoutParams(
                ExtendableListView.LayoutParams.MATCH_PARENT,
                ExtendableListView.LayoutParams.MATCH_PARENT, 1.0f));

        if (savedInstanceState == null) {
            final LayoutInflater layoutInflater = getActivity().getLayoutInflater();


        }
        if (mAdapter == null) {
            mAdapter = new AdapterProfile(getActivity(), R.id.txt_line1);
        }
        mAdapter.notifyDataSetChanged();
        for (int i = 0; i < socialMedia.length; i++){
            if(!r[i].equals("")) {
                if(!socialMedia[i].equals("tumblr")) {
                    mData.add(new User(socialMedia[i], r[i], "http://192.168.137.1/myphp/joiner.com/app/images/" + socialMedia[i] + ".png",
                            socialMedia[i], ""));
                }else{
                    mData.add(new User(socialMedia[i], r[i]+".tumblr.com", "http://192.168.137.1/myphp/joiner.com/app/images/" + socialMedia[i] + ".png",
                            socialMedia[i], ""));
                }
            }
        }


        for (User data : mData) {
            mAdapter.add(data);

        }
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount){
                if (!mHasRequestedMore) {
                    int lastInScreen = firstVisibleItem + visibleItemCount;
                    if (lastInScreen >= totalItemCount) {
                        Log.d("test", "onScroll lastInScreen - so load more");
                        mHasRequestedMore = true;
                        onLoadMoreItems();
                    }
                }
            }

        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Toast.makeText(getActivity(), "Item Clicked: " + position, Toast.LENGTH_SHORT).show();
                switch (mData.get(position).getId()) {

                    case "IDFORSHARE":copyToClipboard(mData.get(position).getName());
                        break;
                    case "whatsapp":openDailerWhatsapp(mData.get(position).getName());
                        break;
                    case "askfm":

                        copyToClipboard(mData.get(position).getName());
                        break;
                    case "google":
                        copyToClipboard(mData.get(position).getName());
                        break;
                    case "instagram":
                        copyToClipboard(mData.get(position).getName());
                        break;
                    case "twitter":
                       copyToClipboard(mData.get(position).getName());
                        break;
                    case "reddit":
                       copyToClipboard(mData.get(position).getName());
                        break;
                    case "tumblr":
                        copyToClipboard(mData.get(position).getName());
                        break;
                    case "linkedin":
                        copyToClipboard(mData.get(position).getName());
                        break;
                    case "flickr":
                        copyToClipboard(mData.get(position).getName());
                        break;
                    case "pinterest":
                        copyToClipboard(mData.get(position).getName());
                        break;
                    case "youtube":
                        copyToClipboard(mData.get(position).getName());
                        break;
                    case "wechat":
                        copyToClipboard(mData.get(position).getName());
                        break;
                    case "snapchat":
                        copyToClipboard(mData.get(position).getName());
                        break;
                    case "facebook":
                        copyToClipboard(mData.get(position).getName());
                        break;

                }
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                switch (mData.get(position).getId()) {

                    case "askfm":
                        openPage("http://ask.fm/" + mData.get(position).getName());

                        break;
                    case "google":
                        openPage("https://plus.google.com/s/" + mData.get(position).getName());

                        break;
                    case "instagram":
                        openPage("https://www.instagram.com/" + mData.get(position).getName());

                        break;
                    case "twitter":
                        openPage("https://twitter.com/" + mData.get(position).getName());

                        break;
                    case "reddit":
                        openPage("https://www.reddit.com/user/" + mData.get(position).getName());

                        break;
                    case "tumblr":
                        openPage("http://" + mData.get(position).getName()+".tumblr.com");

                        break;
                    case "linkedin":
                        openPage("https://www.linkedin.com/vsearch/f?type=all&keywords=" + mData.get(position).getName());

                        break;
                    case "flickr":
                        openPage("https://www.flickr.com/photos/" + mData.get(position).getName());

                        break;
                    case "pinterest":
                        openPage("https://pinterest.com/" + mData.get(position).getName());

                        break;
                    case "youtube":
                        openPage("https://www.youtube.com/results?search_query=" + mData.get(position).getName());

                        break;
                    case "facebook":
                        openPage("https://www.facebook.com/" + mData.get(position).getId());
                        break;
                }
                return false;
            }


        });


        return view;
    }
    private void onLoadMoreItems() {
       /* final ArrayList<String> sampleData = Data.generateSampleData();
        for (String data : sampleData) {
            mAdapter.add(data);
        }
        // stash all the data in our backing store
        mData.addAll(sampleData);
        // notify the adapter that we can update now
        mAdapter.notifyDataSetChanged();
        mHasRequestedMore = false;*/
    }

    public User retrieveUser(){
        Gson gson = new Gson();
        String json = mPrefs.getString("LoggedInUser", "");
        User obj = gson.fromJson(json, User.class);
        return obj;

    }
    public void openPage(String url){
       Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void copyToClipboard(String text){
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Info", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getContext(), "Copied to clipboards (now just paste where you would to use it!)", Toast.LENGTH_LONG).show();

    }
    public void openDailerWhatsapp(String text){
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+text));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().getApplicationContext().startActivity(intent);
    }


}
