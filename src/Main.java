import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage stage;
    static Scene loginScreen;
    static Scene createAccountScreen;
    static Scene userScreen;
    static Scene adminScreen;


    static DummyData dummyData;

    @Override
    public void start(Stage primaryStage) throws Exception{

        dummyData = new DummyData();
        dummyData.initializeDummyData();

        primaryStage.setTitle("Log in");
        primaryStage.setResizable(false);
        stage = primaryStage;

        loginScreen = new Scene(FXMLLoader.load(getClass().getResource("FXML/login_screen.fxml")));
        createAccountScreen = new Scene(FXMLLoader.load(getClass().getResource("FXML/create_account_screen.fxml")));
        userScreen = new Scene(FXMLLoader.load(getClass().getResource("FXML/user_screen.fxml")));
        adminScreen = new Scene(FXMLLoader.load(getClass().getResource("FXML/admin_screen.fxml")));
        primaryStage.setScene(loginScreen);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
