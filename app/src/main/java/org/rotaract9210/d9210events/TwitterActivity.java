package org.rotaract9210.d9210events;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.rotaract9210.d9210events.SharedClasses.TwitterPostArrayAdapter;

import java.util.ArrayList;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


/**
 * Created by Leo on 9/12/2016.
 */
public class TwitterActivity  extends AppCompatActivity{

    final static String CONSUMER_KEY = "9GNf1ycClbDmdFqyA9ArgNYdb";
    final static String CONSUMER_SECRET = "lE6ySjno0uAWMQRyYiIHpL51NqL5eLqR4VDjANpwxnZYocGiYK";
    final static String ACCESS_TOKEN = "931340078-xwCjaChPV2uiExTf5sYp0J00Nbbb4KVsJYOYnj70";
    final static String ACCESS_TOKEN_SECRET = "8utxpQFQGGBY5xSpAUpizoD7BFCvjxvGKA8POIgq6dPMr";
    final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";

    TwitterPostArrayAdapter adapter;
    ListView listView;
    EditText etSearch;
    ImageButton ibNavButton,ibSearch;
    static String search = "rotaractd9210";
    final static String LOG_TAG = "rnc";
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        String eventName;

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Searching...");
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //cancel search;
            }
        });

        etSearch = (EditText)findViewById(R.id.etToolbar_Search_Box);
        ibSearch = (ImageButton)findViewById(R.id.ibToolbar_Search);
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search = etSearch.getText().toString();
                downloadTweets(search);
                //downloadTweets();
            }
        });
        listView = (ListView)findViewById(R.id.lvTwitter_List);
        adapter = new TwitterPostArrayAdapter(getApplicationContext(),downloadTweets(search));
        // send the tweets to the adapter for rendering
        listView.setAdapter(adapter);
    }

    public ArrayList<Status> downloadTweets(final String search) {
        final ArrayList<twitter4j.Status> tweets = new ArrayList<>();
        new AsyncTask<Void,Void,String>(){
            @Override
            protected void onPostExecute(String s) {
                progressDialog.hide();
            }

            @Override
            protected void onPreExecute() {
                progressDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                ConfigurationBuilder cb = new ConfigurationBuilder();
                cb.setOAuthConsumerKey(CONSUMER_KEY);
                cb.setOAuthConsumerSecret(CONSUMER_SECRET);
                cb.setOAuthAccessToken(ACCESS_TOKEN);
                cb.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

                Twitter twitter = new TwitterFactory(cb.build()).getInstance();
                Query query = new Query(search);
                int numberOfTweets = 40;
                long lastID = Long.MAX_VALUE;

                while (tweets.size () < numberOfTweets) {
                    if (numberOfTweets - tweets.size() > 40)
                        query.setCount(40);
                    else
                        query.setCount(numberOfTweets - tweets.size());
                    try {
                        QueryResult result = twitter.search(query);
                        tweets.addAll(result.getTweets());
                        Log.i("TWEETS_TESTING","Gathered " + tweets.size() + " tweets");
                        for (twitter4j.Status t: tweets)
                            if(t.getId() < lastID) lastID = t.getId();

                    } catch (TwitterException te) {
                        Log.v("TWEET_ERROR","Couldn't connect: " + te);
                    }
                    query.setMaxId(lastID-1);
                    if (!progressDialog.isShowing()) break;
                }
                return null;
            }
        }.execute();
        return tweets;
    }
}