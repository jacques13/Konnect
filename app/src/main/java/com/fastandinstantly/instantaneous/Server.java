package com.fastandinstantly.instantaneous;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 2016/12/03.
 */

public class Server {

    public String getUrl() {
        return url;
    }

    String url = "http://10.0.0.4/myphp/joiner.com/app/";
    public String[] ResultForUser;
    public List<User> users = new ArrayList<User>();
    public String[] info  = new String[15];
    private String[] socialMedia = {"facebook","instagram","snapchat"
            ,"tumblr","twitter","linkedin",
            "pinterest","reddit","wechat","whatsapp",
            "youtube","askfm","flickr","google"};



    public void getUsers(){
        String result = "";
        InputStream isr = null;
        StrictMode.enableDefaults();
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url+"getUsers.php"); //YOUR PHP SCRIPT ADDRESS
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();

        }
        catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
            //textView.setText("Couldnt connect to database");
        }
        //convert response to string
        try{
            result = inputStreamToString(isr);
            result.replaceAll("\\s+","");

            isr.close();
        }
        catch(Exception e){
            Log.e("log_tag", "Error  converting result " + e.toString());
        }

        //parse json data
        try {
            String s = "";
            JSONArray jArray = new JSONArray(result);


            int x=0;
            while (x<jArray.length()){
                JSONObject json = jArray.getJSONObject(x);
                Log.i("test",json.getString("name"));
                users.add(new User(json.getString("ID"),
                                    json.getString("name"),
                                    json.getString("image"),
                                    json.getString("gender")
                                    ,json.getString("email")));
                x++;

            }




        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
            //textView.setText("error");
        }

    }
    public List<User> getUsersList() {
        return users;
    }

    public String[] getInfo(String ID){
        String result = "";
        InputStream isr = null;
        StrictMode.enableDefaults();
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url+"getInfo.php?ID="+ID); //YOUR PHP SCRIPT ADDRESS
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();

        }
        catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
            //textView.setText("Couldnt connect to database");
        }
        //convert response to string
        try{
            result = inputStreamToString(isr);
            result.replaceAll("\\s+","");

            isr.close();
        }
        catch(Exception e){
            Log.e("log_tag", "Error  converting result " + e.toString());
        }

        //parse json data
        try {
            String s = "";

            JSONArray jArray = new JSONArray(result);

            JSONObject json = jArray.getJSONObject(0);
            Log.i("check",info[0]+" info");
            Log.i("check",info[0]+" s.info");
            for (int i=0;i<socialMedia.length;i++){

                info[i] = json.getString(socialMedia[i]);

            }
            Log.i("check","Server Class");
            Log.i("check",info[0]+" info");
             return info;


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
            //textView.setText("error");
        }

        return null;
    }

    public void sendUserData(String ID, String name, String email, String image, String gender){
        String result = "";
        InputStream isr = null;
        StrictMode.enableDefaults();
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url+"userData.php?ID="+ID+"&name="+name+"&email="+email+"&image="+image+"&gender="+gender);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();

        }
        catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
            //textView.setText("Couldnt connect to database");
        }
        //convert response to string
        try{

            result = inputStreamToString(isr);
            result.replaceAll("\\s+","");

            isr.close();



            // Log.e("log_tag", result);
        }
        catch(Exception e){
            Log.e("log_tag", "Error  converting result " + e.toString());
        }

        //parse json data
        try {
            String s = "";
            JSONArray jArray = new JSONArray(result);
            JSONObject json = jArray.getJSONObject(0);
            ResultForUser[0] = json.getString("Result");
         } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
            //textView.setText("error");
        }

    }
    public void sendUserInfo(String ID, String facebook, String instagram, String snapchat, String tumblr,String twitter, String linkedin, String pinterst,String reddit, String wechat, String whatsapp,String youtube, String askfm,String flickr,String google){
        String result = "";
        InputStream isr = null;
        StrictMode.enableDefaults();
        String urli = url+"sendData.php?ID="+ID+"&facebook="+facebook+"&instagram="+instagram+
                                        "&snapchat="+snapchat+"&tumblr="+tumblr+"&twitter="+twitter
                                        +"&linkedin="+linkedin +"&pinterst="+pinterst +"&reddit="+reddit
                                        +"&wechat="+wechat +"&whatsapp="+whatsapp +"&youtube="+youtube
                                        +"&askfm="+askfm +"&flickr="+flickr +"&google="+google;
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(urli);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();

        }
        catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
            //textView.setText("Couldnt connect to database");
        }
        //convert response to string
        try{

            result = inputStreamToString(isr);
            result.replaceAll("\\s+","");

            isr.close();



            // Log.e("log_tag", result);
        }
        catch(Exception e){
            Log.e("log_tag", "Error  converting result " + e.toString());
        }

        //parse json data
        try {
            String s = "";
            JSONArray jArray = new JSONArray(result);
            JSONObject json = jArray.getJSONObject(0);
            ResultForUser[0] = json.getString("Result");
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
            //textView.setText("error");
        }

    }
    private static String getCleanURL(String url){
        return url.replaceAll("\\\\", "").trim();
    }
    private String inputStreamToString(InputStream is){

        String line = "";
        StringBuilder total = new StringBuilder();
        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is), 1024 * 4);
        // Read response until the end
        try
        {

            while ((line = rd.readLine()) != null)
            {
                total.append(line);
            }
        } catch (IOException e)
        {
            Log.e("log_tag", "error build string" + e.getMessage());
        }
        // Return full string
        return total.toString();
    }
}
