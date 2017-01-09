package org.rotaract9210.d9210events.SharedClasses;

import android.graphics.Bitmap;

/**
 * Created by Leo on 8/16/2016.
 */
public class TwitterPost {

    private String name;
    private String body;
    private String date;
    private Bitmap postImage;

    public TwitterPost(String name, String body, String date) {

        this.name = name;
        this.body = body;
        this.date = date;
        //this.postImage = postImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Bitmap getPostImage() {
        return postImage;
    }

    public void setPostImage(Bitmap postImage) {
        this.postImage = postImage;
    }
}
