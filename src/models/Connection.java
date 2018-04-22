package models;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class Connection implements Serializable {
    private Station from;
    private Station to;
    //String from;
    //String to;
    private LocalTime length;
    private int price;
    private ArrayList<LocalTime> times;

    public Connection(Station from,Station to, int price, LocalTime length){
        this.from = from;
        this.to = to;
        this.price = price;
        this.length = length;
    }

    public ArrayList<LocalTime> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<LocalTime> times) {
        this.times = times;
    }

    /*public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }*/

    public Station getFrom() {
        return from;
    }

    public Station getTo() {

        return to;
    }

    public LocalTime getLength() {
        return length;
    }

   /* public void setFrom(Station from) {
        this.from = from;
    }

    public void setTo(Station to) {
        this.to = to;
    }*/

    public void setLength(LocalTime length) {
        this.length = length;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
