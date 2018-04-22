package models;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class Connection implements Serializable {
    private Station from;
    private Station to;
    private LocalTime length;
    private ArrayList<LocalTime> times;

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


    public Station getFrom() {
        return from;
    }

    public Station getTo() {

        return to;
    }

    public LocalTime getLength() {
        return length;
    }



    public void setLength(LocalTime length) {
        this.length = length;
    }

}
