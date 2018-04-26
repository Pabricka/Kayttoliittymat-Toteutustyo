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

    public static void main(String args[]) {

        users = new ArrayList<>();
        trips = new ArrayList<>();
        trains = new ArrayList<>();
        purchases = new ArrayList<>();
        trips = new ArrayList<>();
        connections = new ArrayList<>();
        carTypes = new ArrayList<>();

        initializeDummyData();
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

    @Override
    public void createNewUser(String name, String address, String username, String password, Boolean admin) {
        users.add(new User(name, address, username, password, admin));

    }

    private static void initializeDummyData() {
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

    @Override
    public ArrayList<User> getUsers() throws RemoteException {
        return users;
    }

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

    @Override
    public ArrayList<Trip> getTrips() throws RemoteException {
        return trips;
    }

    @Override
    public ArrayList<Purchase> getPurchases() throws RemoteException {
        return purchases;
    }

    @Override
    public void newPurchase(Purchase p) throws RemoteException {
        purchases.add(p);
    }

    @Override
    public ArrayList<Car> getCarTypes() throws RemoteException {
        return carTypes;
    }

    @Override
    public void addCarType(Car car) throws RemoteException {
        carTypes.add(car);
    }
}