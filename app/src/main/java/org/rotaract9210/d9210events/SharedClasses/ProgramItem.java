package org.rotaract9210.d9210events.SharedClasses;

/**
 * Created by Leom on 2017/01/10.
 */

public class ProgramItem {
    public String day;
    public String date;
    public String time;
    public String session;
    public String facilitator;
    public String SaA;

    public ProgramItem(String day, String date, String time, String session, String facilitator, String saA) {
        this.day = day;
        this.date = date;
        this.time = time;
        this.session = session;
        this.facilitator = facilitator;
        SaA = saA;
    }
}
