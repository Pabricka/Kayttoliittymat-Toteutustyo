package server;


import models.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface DummyData extends Remote {

    void createNewUser(String name, String address, String username, String password, Boolean admin) throws RemoteException;
    ArrayList<User> getUsers() throws RemoteException;
    void changeName(String username, String s) throws RemoteException;
    void changeUsername(String username, String s) throws RemoteException;
    void changePassword(String username, String s) throws RemoteException;
    void changeAddress(String username, String s) throws RemoteException;
    ArrayList<Trip> getTrips() throws RemoteException;
    ArrayList<Purchase> getPurchases() throws RemoteException;
}