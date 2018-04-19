package Models;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class Connection implements Serializable {
    Station from;
    Station to;
    //String from;
    //String to;
    LocalTime length;
    ArrayList<LocalTime> times;

    public Connection(Station from,Station to,LocalTime length){
        this.from = from;
        this.to = to;
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
}
