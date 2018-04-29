package server;


import models.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Necessary interface to make server work
 */
public interface DummyData extends Remote {

    void createNewUser(String name, String address, String username, String password, Boolean admin) throws RemoteException;
    ArrayList<User> getUsers() throws RemoteException;
    void changeName(String username, String s) throws RemoteException;
    void changeUsername(String username, String s) throws RemoteException;
    void changePassword(String username, String s) throws RemoteException;
    void changeAddress(String username, String s) throws RemoteException;
    ArrayList<Car> getCarTypes() throws RemoteException;
    void addCarType(Car car) throws RemoteException;
    ArrayList<Trip> getTrips() throws RemoteException;
    ArrayList<Purchase> getPurchases() throws RemoteException;
    void newPurchase(Purchase p) throws RemoteException;
    ArrayList<Train> getTrains()throws RemoteException;
    void setTrain(int index,Train train)throws RemoteException;
    Station[] getStations()throws RemoteException;
    ArrayList<Connection> getConnections() throws RemoteException;
    void addConnection(Connection connection) throws RemoteException;
    void removeConnection(int i) throws RemoteException;
    void removeJourney(int i)throws RemoteException;
    void temporalReservation(Trip trip, int car, int seat, boolean reservation) throws RemoteException;
}