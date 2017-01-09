package org.rotaract9210.d9210events.SharedClasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Leo on 9/26/2016.
 */
public class Event {
    @SerializedName("event_name")
    String event_name;
    @SerializedName("event_date")
    String event_date;
    @SerializedName("event_program")
    String event_program;
    @SerializedName("event_time")
    String event_time;
    @SerializedName("event_details")
    String event_details;
    @SerializedName("event_about")
    String event_about;
    @SerializedName("event_cost")
    String event_cost;
    @SerializedName("event_location")
    String event_location;
    @SerializedName("event_picture")
    String event_picture;


    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_program() {
        return event_program;
    }

    public void setEvent_program(String event_program) {
        this.event_program = event_program;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_details() {
        return event_details;
    }

    public void setEvent_details(String event_details) {
        this.event_details = event_details;
    }

    public String getEvent_about() {
        return event_about;
    }

    public void setEvent_about(String event_about) {
        this.event_about = event_about;
    }

    public String getEvent_cost() {
        return event_cost;
    }

    public void setEvent_cost(String event_cost) {
        this.event_cost = event_cost;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_picture() {
        return event_picture;
    }

    public void setEvent_picture(String event_picture) {
        this.event_picture = event_picture;
    }
}
