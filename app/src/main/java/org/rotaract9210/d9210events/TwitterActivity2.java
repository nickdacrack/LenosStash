






package org.rotaract9210.d9210events;

        import android.app.ListActivity;
        import android.content.Context;
        import android.content.Intent;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Base64;
        import android.util.Log;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.google.gson.Gson;
        import com.loopj.android.http.AsyncHttpClient;
        import com.loopj.android.http.AsyncHttpResponseHandler;

        import org.rotaract9210.d9210events.SharedClasses.Authenticated;
        import org.rotaract9210.d9210events.SharedClasses.Tweet;
        import org.rotaract9210.d9210events.SharedClasses.Twitter;
        import org.rotaract9210.d9210events.SharedClasses.TwitterPost;
        import org.rotaract9210.d9210events.SharedClasses.TwitterPostArrayAdapter;
        import org.rotaract9210.d9210events.SharedClasses.TwitterUser;

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.io.UnsupportedEncodingException;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.ProtocolException;
        import java.net.URL;
        import java.net.URLEncoder;

public class TwitterActivity2 extends AppCompatActivity {

    static org.rotaract9210.d9210events.SharedClasses.Twitter twits;
    TwitterPostArrayAdapter adapter;
    ListView listView;
    EditText etSearch;
    ImageButton ibNavButton,ibSearch;
    static String search = "rotaractd9210";
    final static String LOG_TAG = "rnc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);

        etSearch = (EditText)findViewById(R.id.etToolbar_Search_Box);
        ibSearch = (ImageButton)findViewById(R.id.ibToolbar_Search);
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    search = URLEncoder.encode(etSearch.getText().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                downloadTweets(search);
            }
        });
        listView = (ListView)findViewById(R.id.lvTwitter_List);
        downloadTweets(search);
    }

    // download twitter timeline after first checking to see if there is a network connection
    public void downloadTweets(String searchTerm) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadTwitterTask().execute(searchTerm);
        } else {
            Log.v(LOG_TAG, "No network connection available.");
        }
    }

    public class Authenticated {
        String token_type;
        String access_token;
    }

    // Uses an AsyncTask to download a Twitter user's timeline
    private class DownloadTwitterTask extends AsyncTask<String, Void, String> {
        final static String CONSUMER_KEY = "9GNf1ycClbDmdFqyA9ArgNYdb";
        final static String CONSUMER_SECRET = "lE6ySjno0uAWMQRyYiIHpL51NqL5eLqR4VDjANpwxnZYocGiYK";
        final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
        //final static String TwitterStreamURL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";
        final static String TwitterStreamURL = "https://api.twitter.com/1.1/search/tweets.json?q=";
        @Override
        protected String doInBackground(String... screenNames) {
            String result = null;

            if (screenNames.length > 0) {
                result = getTwitterStream(screenNames[0]);
            }
            return (result==null)? "no response" : result;
        }

        // onPostExecute convert the JSON results into a Twitter object (which is an Array list of tweets
        @Override
        protected void onPostExecute(String result) {

            if (result.equals("something went wrong. Try again later")){
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            } else{
                twits = jsonToTwitter(result);
                //adapter = new TwitterPostArrayAdapter(getApplicationContext(),twits);
                // send the tweets to the adapter for rendering
                listView.setAdapter(adapter);
            }
        }

        // converts a string of JSON data into a Twitter object
        private org.rotaract9210.d9210events.SharedClasses.Twitter jsonToTwitter(String result) {
            org.rotaract9210.d9210events.SharedClasses.Twitter twits = null;
            if (result != null && result.length() > 0) {
                try {
                    Gson gson = new Gson();
                    twits = gson.fromJson(result, org.rotaract9210.d9210events.SharedClasses.Twitter.class);
                } catch (IllegalStateException ex) {
                    // just eat the exception
                    Log.v("TWITTER_CONNECTION: ",ex.toString());
                }
            }
            return twits;
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
                Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
            } catch (IOException ex2) {
                Toast.makeText(getApplicationContext(),ex2.toString(),Toast.LENGTH_LONG).show();
            }
            return sb.toString();
        }
    }
}
