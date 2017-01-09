package org.rotaract9210.d9210events;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.rotaract9210.d9210events.SharedClasses.GridViewAdapter;
import org.rotaract9210.d9210events.SharedClasses.ImageItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class GoogleDrivePicsFragment extends Fragment {

    private GridView gridView;
    private GridViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_google_drive_pics, container, false);
        //adapter = new ArrayAdapter<String>(rootView.getContext(),R.layout.row_layout,R.id.tvRow_Post_Headline, listTitles);

/*        gridView = (GridView)rootView.findViewById(R.id.gridView);
        adapter = new GridViewAdapter(rootView.getContext(),getData());
        gridView.setAdapter(adapter);*/
        // Inflate the layout for this fragment
        return rootView;
    }

    private ArrayList<ImageItem> getData(){
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0;i<imgs.length();i++){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap,"Image#"+i));
        }
        return imageItems;
    }





    final static String CONSUMER_KEY = "9GNf1ycClbDmdFqyA9ArgNYdb";
    final static String CONSUMER_SECRET = "lE6ySjno0uAWMQRyYiIHpL51NqL5eLqR4VDjANpwxnZYocGiYK";
    final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
    final static String TwitterStreamURL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";


    private ArrayList<ImageItem> getGoogleCloudPics(){
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0;i<imgs.length();i++){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap,"Image#"+i));
        }
        return imageItems;
    }

    public class Authenticated {
        String token_type;
        String access_token;
    }

    private String getTwitterStream(String screenName) {
        String results = null;

        // Step 1: Encode consumer key and secret
        try {
            // URL encode the consumer key and secret
            String urlApiKey = URLEncoder.encode(CONSUMER_KEY, "UTF-8");
            String urlApiSecret = URLEncoder.encode(CONSUMER_SECRET, "UTF-8");

            // Concatenate the encoded consumer key, a colon character, and the
            // encoded consumer secret
            String combined = urlApiKey + ":" + urlApiSecret;

            // Base64 encode the string
            String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);
            // Step 2: Obtain a bearer token
            URL url = new URL(TwitterTokenURL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Authorization", "Basic " + base64Encoded);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            connection.connect();

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write("grant_type=client_credentials");
            writer.flush();
            writer.close();
            os.close();

            String rawAuthorization = getResponseBody(connection);
            Authenticated auth = jsonToAuthenticated(rawAuthorization);

            // Applications should verify that the value associated with the
            // token_type key of the returned object is bearer
            if (auth != null && auth.token_type.equals("bearer")) {

                // Step 3: Authenticate API requests with bearer token
                url = new URL(TwitterStreamURL + screenName);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + auth.access_token);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.connect();
                // update the results with the body of the response
                results = getResponseBody(connection);
            }
        } catch (UnsupportedEncodingException ex) {
        } catch (IllegalStateException ex1) {
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    // convert a JSON authentication object into an Authenticated object
    private Authenticated jsonToAuthenticated(String rawAuthorization) {
        Authenticated auth = null;
        if (rawAuthorization != null && rawAuthorization.length() > 0) {
            try {
                Gson gson = new Gson();
                auth = gson.fromJson(rawAuthorization, Authenticated.class);
            } catch (IllegalStateException ex) {
                // just eat the exception
            }
        }
        return auth;
    }
    private String getResponseBody(HttpURLConnection request) {
        BufferedReader br;
        StringBuilder sb = new StringBuilder();
        try {

            int statusCode = request.getResponseCode();
            String reason = request.getResponseMessage();

            if (statusCode == 200) {

                br = new BufferedReader(new InputStreamReader((request.getInputStream())));
                sb = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                    return sb.toString();
                }
            } else {
                sb.append(reason);
            }
        } catch (UnsupportedEncodingException ex) {
            Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException ex2) {
            Toast.makeText(getContext(),ex2.toString(),Toast.LENGTH_LONG).show();
        }
        return sb.toString();
    }
}
