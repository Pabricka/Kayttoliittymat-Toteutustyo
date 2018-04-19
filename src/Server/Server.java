package Server;

import Models.*;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class Server implements DummyData {

    private static ArrayList<User> users;
    private static ArrayList<Connection> connections;
    private static ArrayList<Journey> journeys;

    public static void main(String args[]){

        users = new ArrayList<>();
        journeys = new ArrayList<>();
        connections = new ArrayList<>();

        initializeDummyData();
        try {
            Server obj = new Server();
            DummyData stub = (DummyData) UnicastRemoteObject.exportObject(obj, 1);

            // Bind the remote object's stub in the registry
            System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
            Registry registry = LocateRegistry.createRegistry(1099);

            System.out.println( InetAddress.getLocalHost().getHostAddress());
            registry.bind("DummyData", stub);

            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }


    @Override
    public void createNewUser(String name, String username, String password, Boolean admin) {
        users.add(new User(name,username,password,admin));

    }

    private static void initializeDummyData() {
        users.add(new User("Juha Mieto", "Mämmi666", "1234", false));
        users.add(new User("Jari Mentula", "Bull", "maitorahka", true));
        users.add(new User("Alvin J", "Ellis", "dolor", false));
        users.add(new User("Beverly Z", "Lucas", "Nullam", false));
        users.add(new User("Odysseus I", "Russo", "bibendum", false));
        users.add(new User("Carlos U", "Meyers", "Aliquam", false));
        users.add(new User("Kevin M", "Monroe", "ante", false));
        users.add(new User("Clare W", "Hammond", "Quisque", false));
        users.add(new User("Sonya A", "Hopper", "mollis", false));
        users.add(new User("admin", "a", "a", true));

        connections.add(new Connection(Station.HELSINKI, Station.TURKU, LocalTime.parse("01:57")));
        connections.add(new Connection(Station.TURKU, Station.TAMPERE, LocalTime.parse("01:47")));
        connections.add(new Connection(Station.SEINÄJOKI, Station.VAASA, LocalTime.parse("00:47")));
        connections.add(new Connection(Station.JYVÄSKYLÄ, Station.JOENSUU, LocalTime.parse("03:11")));
        connections.add(new Connection(Station.OULU, Station.KAJAANI, LocalTime.parse("02:14")));
        connections.add(new Connection(Station.KOUVOLA, Station.HELSINKI, LocalTime.parse("01:19")));


        ArrayList<LocalTime> taims = new ArrayList<>();
        taims.add(LocalTime.parse("12:00"));
        taims.add(LocalTime.parse("15:00"));
        taims.add(LocalTime.parse("17:00"));
        taims.add(LocalTime.parse("20:00"));

        for (Connection connection : connections) {
            connection.setTimes(taims);
        }
        Random rnd = new Random();
        for(int i= 0; i<=20;i++){
            journeys.add(new Journey(users.get(rnd.nextInt(users.size())),connections.get(rnd.nextInt(connections.size()))));
        }

    }

    @Override
    public ArrayList<User> getUsers() throws RemoteException {
        return users;
    }

    @Override
    public void changeName(int i, String s) throws RemoteException {
        users.get(i).setName(s);

    }

    @Override
    public void changeUsername(int i, String s) throws RemoteException {
        users.get(i).setUsername(s);

    }

    @Override
    public void changePassword(int i, String s) throws RemoteException {
        users.get(i).setPassword(s);
    }
    @Override
    public ArrayList<Journey> getJourneys()throws RemoteException {
        return journeys;
    }


}
