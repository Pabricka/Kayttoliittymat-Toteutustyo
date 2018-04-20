package models;

import java.io.Serializable;
import java.time.LocalDate;

public class Journey implements Serializable{
    private Train train;
    private User buyer;
    private Connection connection;
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Journey( User buyer, Connection connection) {
       // this.train = train;
        this.buyer = buyer;
        this.connection = connection;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public String getStrings(){
        String information = buyer.getName() + "  " + connection.getFrom().toString() + "  " + connection.getTo().toString();
        return information;
    }
}
