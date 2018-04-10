import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage stage;
    static Scene loginScreen;
    static Scene createAccountScreen;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Log in");
        primaryStage.setResizable(false);
        stage = primaryStage;

        loginScreen = new Scene(FXMLLoader.load(getClass().getResource("login_screen.fxml")));
        createAccountScreen = new Scene(FXMLLoader.load(getClass().getResource("create_account_screen.fxml")));

        primaryStage.setScene(loginScreen);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
