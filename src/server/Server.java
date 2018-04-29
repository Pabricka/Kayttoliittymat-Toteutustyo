package server;

import models.*;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class Server implements DummyData {

    private static ArrayList<User> users;
    private static ArrayList<Connection> connections;
    private static ArrayList<Car> carTypes;
    private static ArrayList<Train> trains;
    private static ArrayList<Trip> trips;
    private static ArrayList<Purchase> purchases;
    private static Station[] stations;

    public static void main(String args[]) {

        users = new ArrayList<>();
        trips = new ArrayList<>();
        trains = new ArrayList<>();
        purchases = new ArrayList<>();
        trips = new ArrayList<>();
        connections = new ArrayList<>();
        carTypes = new ArrayList<>();
        stations = Station.values();

        //create dummydata
        initializeDummyData();

        /*
         * open the RMI server and listen to port 1099
         */
        try {
            Server obj = new Server();
            DummyData stub = (DummyData) UnicastRemoteObject.exportObject(obj, 1);

            // Bind the remote object's stub in the registry
            System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
            Registry registry = LocateRegistry.createRegistry(1099);

            System.out.println(InetAddress.getLocalHost().getHostAddress());
            registry.bind("DummyData", stub);

            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * adds a new user to the database
     * @param name users name
     * @param address users address
     * @param username users username
     * @param password users password
     * @param admin boolean if user is admin or not
     */
    @Override
    public void createNewUser(String name, String address, String username, String password, Boolean admin) {
        users.add(new User(name, address, username, password, admin));

    }

    /**
     * creates some pre-set and random generated dummydata
     */
    private static void initializeDummyData() {
        //some users
        users.add(new User("Juha Mieto", "1539 Long Street", "Mämmi666", "1234", false));
        users.add(new User("Jari Mentula", "120 Highland Drive", "Bull", "maitorahka", true));
        users.add(new User("Alvin J", "1196 Northwest Boulevard", "Ellis", "dolor", false));
        users.add(new User("Beverly Z", "3641 Charles Street", "Lucas", "Nullam", false));
        users.add(new User("Odysseus I", "3103 Ritter Avenue", "Russo", "bibendum", false));
        users.add(new User("Carlos U", "1973 Gore Street", "Meyers", "Aliquam", false));
        users.add(new User("Kevin M", "1138 Carriage Lane", "Monroe", "ante", false));
        users.add(new User("Clare W", "4168 Newton Street", "Hammond", "Quisque", false));
        users.add(new User("Sonya A", "2680 Sycamore Lake Road", "Hopper", "mollis", false));
        users.add(new User("admin A", "4 Goldfield Rd. Honolulu, HI 96815", "a", "a", true));


        //generate a lot of trains
        for (int i = 0; i < 166; i++) {
            trains.add(new Train("UTU" + i + 1));
        }
        //and some more
        trains.add(new Train("UTU666"));
        trains.add(new Train("Thomas the Tank Engine"));


        carTypes.add(new Car("Passenger car", 20, true, false, false, false));
        carTypes.add(new Car("Pet car", 12, false, false, true, false));
        carTypes.add(new Car("Family car", 15, true, true, false, true));
        carTypes.add(new Car("Paladin car", 8, true, true, false, false));


        //generate cars to trains
        Random rnd = new Random();
        for (Train train : trains) {
            for (int i = 0; i < rnd.nextInt(5) + 1; i++) {
                train.addCar(carTypes.get(rnd.nextInt(carTypes.size())));
            }
        }



        //some connections
        connections.add(new Connection(Station.HELSINKI, Station.TURKU, 20, LocalTime.parse("01:57")));
        connections.add(new Connection(Station.TURKU, Station.TAMPERE, 22, LocalTime.parse("01:47")));
        connections.add(new Connection(Station.SEINÄJOKI, Station.VAASA, 18, LocalTime.parse("00:47")));
        connections.add(new Connection(Station.JYVÄSKYLÄ, Station.JOENSUU, 16, LocalTime.parse("03:11")));
        connections.add(new Connection(Station.OULU, Station.KAJAANI, 21, LocalTime.parse("02:14")));
        connections.add(new Connection(Station.KOUVOLA, Station.HELSINKI, 17, LocalTime.parse("01:19")));


        ArrayList<LocalTime> connectionTimes = new ArrayList<>();
        connectionTimes.add(LocalTime.parse("12:00"));
        connectionTimes.add(LocalTime.parse("15:00"));
        connectionTimes.add(LocalTime.parse("17:00"));
        connectionTimes.add(LocalTime.parse("20:00"));


        //some trips
        int tmpTrainCounter = 0;
        for (Connection connection : connections) {
            connection.setTimes(connectionTimes);
            for (LocalTime time : connectionTimes) {
                for (int i = 0; i < 7; i++) {
                    LocalDate now = LocalDate.now();
                    LocalDate date = now.plusDays(i);
                    trips.add(new Trip(trains.get(tmpTrainCounter), connection, date, time));
                    tmpTrainCounter++;
                }
            }
        }
        //populate trips with purchases
        for (Trip trip : trips) {
            for (int i = 0; i <= rnd.nextInt(trip.getTrain().getSeats()); i++) {

                // ¯\_(ツ)_/¯
                int tmpCar = rnd.nextInt(trip.getTrain().getCars().size());
                int tmpSeat = rnd.nextInt(trip.getTrain().getCars().get(tmpCar).getSeats().size());


                if (trip.getTrain().getCars().get(tmpCar).getSeats().get(tmpSeat).isFree()) {
                    purchases.add(new Purchase(users.get(rnd.nextInt(users.size())), trip, tmpCar, tmpSeat));
                    trip.getTrain().getCars().get(tmpCar).getSeats().get(tmpSeat).setFree(false);
                }
            }
        }

    }

    /**
     * give client the current users
     * @return all users in database
     * @throws RemoteException might occur from communication problems
     */
    @Override
    public ArrayList<User> getUsers() throws RemoteException {
        return users;
    }

    /**
     * changes a certain users name
     * @param username is used as an id
     * @param s users new name
     * @throws RemoteException might occur from communication problems
     */
    @Override
    public void changeName(String username, String s) throws RemoteException {
        System.out.println("Changing " + username + "s name to " + s);
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                u.setName(s);
                System.out.println("Success!");
            }
        }

    }

    /**
     * changes a certain users username
     * @param username used as an id
     * @param s users new username
     * @throws RemoteException might occur from communication problems
     */
    @Override
    public void changeUsername(String username, String s) throws RemoteException {
        System.out.println("Changing " + username + "'s username to " + s);
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                u.setUsername(s);
                System.out.println("Success!");
            }
        }
    }

    /**
     * changes a certain users password
     * @param username used as an id
     * @param s users new password
     * @throws RemoteException might occur from communication problems
     */
    @Override
    public void changePassword(String username, String s) throws RemoteException {
        System.out.println("Changing " + username + "s password to " + s);
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                u.setPassword(s);
                System.out.println("Success!");
            }
        }
    }

    /**
     * changes a certain users address
     * @param username used as an id
     * @param s users new address
     * @throws RemoteException might occur from communication problems
     */
    @Override
    public void changeAddress(String username, String s) throws RemoteException {
        System.out.println("Changing " + username + "s address to " + s);
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                u.setAddress(s);
                System.out.println("Success!");
            }
        }
    }

    /**
     * @return all the trips in the database
     * @throws RemoteException might occur from communication problems
     */
    @Override
    public ArrayList<Trip> getTrips() throws RemoteException {
        return trips;
    }

    /**
     * @return all purchases in the database
     * @throws RemoteException might occur from communication problems
     */
    @Override
    public ArrayList<Purchase> getPurchases() throws RemoteException {
        return purchases;
    }

    /**
     * adds a new purchase to the database
     * @param p purchase to be added
     * @throws RemoteException  might occur from communication problems
     */
    @Override
    public void newPurchase(Purchase p) throws RemoteException {
        for(Trip t : trips){
            if(t.getTrain().getEngine().equals(p.getTrip().getTrain().getEngine())){
                t.getTrain().getCars().get(p.getCarNumber()).getSeats().get(p.getSeatNumber()).setTemporalReservation(false);
                t.getTrain().getCars().get(p.getCarNumber()).getSeats().get(p.getSeatNumber()).setFree(false);
            }
        }
        purchases.add(p);
    }

    /**
     * creates a temporary reservation
     * @param trip the trip that the reservation will be on
     * @param car the car where the seat is that is reserved
     * @param seat the seat that is reserved
     * @param reservation will seat be reserved of freed
     * @throws RemoteException might occur from communication problems
     */

    @Override
    public void temporalReservation(Trip trip, int car, int seat, boolean reservation) throws RemoteException {
        System.out.println("Reserving...");
        for(Trip t : trips){
            if(t.getTrain().getEngine().equals(trip.getTrain().getEngine())){
                t.getTrain().getCars().get(car).getSeats().get(seat).setTemporalReservation(reservation);
                System.out.println("Seat reserved:" + trip.toString() + car + " " + seat);
            }
        }
    }


    /**
     * @return all car types in the database
     * @throws RemoteException might occur from communication problems
     */
    @Override
    public ArrayList<Car> getCarTypes() throws RemoteException {
        return carTypes;
    }

    /**
     * adds a new car to the database
     * @param car to be added
     * @throws RemoteException  might occur from communication problems
     */
    @Override
    public void addCarType(Car car) throws RemoteException {
        carTypes.add(car);
    }

    /**
     * @return all trains in the database
     * @throws RemoteException might occur from communication problems
     */
    @Override
    public ArrayList<Train> getTrains() throws RemoteException{
        return trains;
    }

    /**
     * updates a certain train
     * @param index of a train
     * @param train updated train
     * @throws RemoteException  might occur from communication problems
     */
    @Override
    public void setTrain(int index,Train train)throws RemoteException{
        for(Trip t : trips){
            if(t.getTrain().equals(trains.get(index))){
                t.setTrain(train);
            }
        }
        trains.set(index,train);
    }

    /**
     * @return all stations in the database
     * @throws RemoteException might occur from communication problems
     */
    @Override
    public Station[] getStations()throws RemoteException {
        return stations;
    }

    /**
     * @return all connection in the database
     * @throws RemoteException might occur from communication problems
     */
    @Override
    public ArrayList<Connection> getConnections() throws RemoteException{
        return connections;
    }

    /**
     *
     * @param connection that user wants to add to the database
     * @throws RemoteException might occur from communication problems
     */
    @Override
    public void addConnection(Connection connection) throws RemoteException{
        connections.add(connection);
        for(LocalTime time : connection.getTimes()) {
            trips.add(new Trip(trains.get(trains.size() - 1), connection, LocalDate.now(), time));
        }
    }

    /**
     * removes a connection and the trips with that connection
     * @param i the index of the connection that will be deleted
     * @throws RemoteException might occur from communication problems
     */
    @Override
    public void removeConnection(int i) throws RemoteException{
        for(int j = 0; j<trips.size(); j++){
            if(trips.get(j).getConnection().equals(connections.get(i))){
                trips.remove(j);
                j--;
            }
        }
        connections.remove(i);
    }

    /**
     * removes the selected purchase
     * @param i index of the purchase that shall be deleted
     * @throws RemoteException might occur from communication problems
     */
    @Override
    public void removeJourney(int i) throws RemoteException{
        purchases.get(i).getTrip().getTrain().getCars().get(purchases.get(i).getCarNumber()).getSeats().get(purchases.get(i).getSeatNumber()).setFree(true);
        purchases.remove(i);
    }
}