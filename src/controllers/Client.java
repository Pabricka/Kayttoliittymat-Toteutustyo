package controllers;

import models.User;
import server.DummyData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client extends Application {


    static User currentUser;

    static Stage stage;
    static Scene loginScreen;
    static Scene createAccountScreen;
    static Scene userScreen;
    static Scene adminScreen;
    static DummyData dummyData;
    @Override
    public void start(Stage primaryStage) throws Exception{


        primaryStage.setTitle("Log in");
        primaryStage.setResizable(false);
        stage = primaryStage;

        loginScreen = new Scene(FXMLLoader.load(getClass().getResource("/FXML/login_screen.fxml")));
        createAccountScreen = new Scene(FXMLLoader.load(getClass().getResource("/FXML/create_account_screen.fxml")));
        userScreen = new Scene(FXMLLoader.load(getClass().getResource("/FXML/user_screen.fxml")));
        adminScreen = new Scene(FXMLLoader.load(getClass().getResource("/FXML/admin_screen.fxml")));
        primaryStage.setScene(loginScreen);
        primaryStage.show();
    }


    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            dummyData = (DummyData) registry.lookup("DummyData");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
        launch(args);
    }
}
