package org.rotaract9210.d9210events.SharedClasses;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Leo on 8/16/2016.
 */
public class EventMessage {
    private String title;
    private String body;
    private String day;
/*    private String time;*/
    private ArrayList<Speakers> speakers = new ArrayList<>();
    private Bitmap postImage;

    public  void addSpeaker(Speakers speaker){
        this.speakers.add(speaker);
    }

    public ArrayList<Speakers> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(ArrayList<Speakers> speakers) {
        this.speakers = speakers;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public EventMessage(String title, String body,String day) {
        this.title = title;
        this.body = body;
        this.day = day;
        /*this.time = time;*/

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getPostImage() {
        return postImage;
    }

    public void setPostImage(Bitmap postImage) {
        this.postImage = postImage;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
