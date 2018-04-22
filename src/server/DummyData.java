package server;


import models.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface DummyData extends Remote {

    void createNewUser(String name, String address, String username, String password, Boolean admin) throws RemoteException;
    ArrayList<User> getUsers() throws RemoteException;
    void changeName(int i, String s) throws RemoteException;
    void changeUsername(int i, String s) throws RemoteException;
    void changePassword(int i, String s) throws RemoteException;
    ArrayList<Trip> getTrips() throws RemoteException;
    ArrayList<Purchase> getPurchases() throws RemoteException;
}
