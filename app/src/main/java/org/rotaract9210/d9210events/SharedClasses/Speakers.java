package org.rotaract9210.d9210events.SharedClasses;

/**
 * Created by Leo on 9/1/2016.
 */
public class Speakers {
    private String name;
    private String profession;
    private String bioBrief;
    private String quote;
    private String topic;
    private int pic = 0;


    public Speakers(String name, String profession, String bioBrief, String quote, String topic, int pic) {
        this.name = name;
        this.profession = profession;
        this.bioBrief = bioBrief;
        this.quote = quote;
        this.topic = topic;
        this.pic = pic;
    }

    public Speakers(String name, String profession, String bioBrief, String quote, String topic) {
        this.name = name;
        this.profession = profession;
        this.bioBrief = bioBrief;
        this.quote = quote;
        this.topic = topic;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getBioBrief() {
        return bioBrief;
    }

    public void setBioBrief(String bioBrief) {
        this.bioBrief = bioBrief;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
