package Server;

import Models.User;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements DummyData {

    private static ArrayList<User> users = new ArrayList<>();

    public static void main(String args[]){

        users = new ArrayList<>();
        initializeDummyData();
        try {
            Server obj = new Server();
            DummyData stub = (DummyData) UnicastRemoteObject.exportObject(obj, 1099);

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


}
