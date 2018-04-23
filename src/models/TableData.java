package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TableData {
    private final SimpleStringProperty departure;
    private final SimpleStringProperty arrival;
    private final SimpleStringProperty length;
    private final SimpleStringProperty type;
    private final SimpleStringProperty price;
    private final SimpleStringProperty services;
    private final Button button;

    public TableData(Trip trip) {
        this.departure = new SimpleStringProperty(trip.getDepartureTime().format(DateTimeFormatter.ofPattern("dd.MM. kk:mm")));

        //Adding trip length to departure
        this.arrival = new SimpleStringProperty(trip.getDepartureTime().plusHours(trip.getConnection().getLength()
                .getHour()).plusMinutes(trip.getConnection().getLength().getMinute()).format(DateTimeFormatter.ofPattern("dd.MM. kk:mm")));

        this.length = new SimpleStringProperty(trip.getConnection().getLength().format(DateTimeFormatter.ofPattern("kk:mm")));
        this.type = new SimpleStringProperty(trip.getTrain().getEngine());
        this.price = new SimpleStringProperty(Integer.toString(trip.getConnection().getPrice()) + "€");
        this.services = new SimpleStringProperty(getServiceString(trip));
        this.button = new Button("Order");
    }

    public String getServiceString(Trip trip) {
        String services = "";
        boolean forTheAllergic = false;
        boolean wheelChairAccess = false;
        boolean petAllowed = false;
        boolean familyCluster = false;
        List<Car> cars = trip.getTrain().getCars();

        for (Car car : cars) {
            if (car.isForTheAllergic()) forTheAllergic = true;
            if (car.isWheelChairAccess()) wheelChairAccess = true;
            if (car.isPetAllowed()) petAllowed = true;
            if (car.isFamilyCluster()) familyCluster = true;
        }
        if (forTheAllergic) services += "Seats for the allergic, ";
        if (wheelChairAccess) services += "Wheelchair access, ";
        if (petAllowed) services += "Seats where pets are allowed, ";
        if (familyCluster) services += "Seat groups for families, ";

        if (services.length() > 0) {
            services = services.substring(0, services.length() - 2);
        }
        return services;
    }

    public String getDeparture() {
        return departure.get();
    }

    public SimpleStringProperty departureProperty() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure.set(departure);
    }

    public String getArrival() {
        return arrival.get();
    }

    public SimpleStringProperty arrivalProperty() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival.set(arrival);
    }

    public String getLength() {
        return length.get();
    }

    public SimpleStringProperty lengthProperty() {
        return length;
    }

    public void setLength(String length) {
        this.length.set(length);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getServices() {
        return services.get();
    }

    public SimpleStringProperty servicesProperty() {
        return services;
    }

    public void setServices(String services) {
        this.services.set(services);
    }

    public Button getButton() {
        return button;
    }
}
