package org.rotaract9210.d9210events.SharedClasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.rotaract9210.d9210events.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import twitter4j.Status;

/**
 * Created by Leo on 8/16/2016.
 */
//public class TwitterPostArrayAdapter extends ArrayAdapter<Tweet> {
public class TwitterPostArrayAdapter extends ArrayAdapter<Status> {
    ArrayList<Status> postList = new ArrayList<>();
    TextView tvTitle,tvBody,tvDate;
    ImageView ivPostPic;

    public TwitterPostArrayAdapter(Context context, ArrayList<Status> objects) {
        super(context, R.layout.layout_twitter_post, objects);
        this.postList = objects;
    }

    public void setPostList(ArrayList<Status> list){
        postList = list;
    }
    @Override
    public void add(Status object) {
        //super.add(object);
        postList.add(object);
    }

    @Override
    public void remove(Status object) {
        super.remove(object);
        postList.remove(object);
    }

    @Override
    public Status getItem(int position) {
        return postList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v==null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.layout_twitter_post, parent,false);
        }

        Status messageobj = getItem(position);
        tvTitle = (TextView)v.findViewById(R.id.tvTwitter_Post_Title);
        tvBody = (TextView)v.findViewById(R.id.tvTwitter_Post_Body);
        tvDate = (TextView)v.findViewById(R.id.tvTwitter_Post_Date);
        ivPostPic = (ImageView)v.findViewById(R.id.ivTwitter_Post_Picture);

        tvTitle.setText(""+messageobj.getUser().getName());
        tvBody.setText("" + messageobj.getText());
        //tvDate.setText("" + messageobj.getDateCreated());
        tvDate.setText("" + messageobj.getCreatedAt());
        //ivPostPic.setImageBitmap(getBitmap(messageobj.getUser().getProfileImageUrl()));
        ivPostPic.setImageBitmap(getBitmap(messageobj.getUser().getBiggerProfileImageURLHttps()));
        return v;
    }

    public Bitmap getBitmap(String bitmapUrl){
        try {
            return new AsyncTask<String,Void,Bitmap>(){

                @Override
                protected Bitmap doInBackground(String... params) {
                    URL url;
                    try {
                        url = new URL(params[0]);
                        return BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute(bitmapUrl).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
