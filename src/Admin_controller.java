import Models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class Admin_controller {
    ArrayList<User> users;

   @FXML
    ListView<String> user_list;

   @FXML
    Text username_text;

   @FXML
    Text name_text;

   @FXML
    Text password_text;

   @FXML
   Text u_text;

   @FXML
   Text n_text;

   @FXML
   Text p_text;

   @FXML
    Button u_button;
   @FXML
   Button n_button;
   @FXML
   Button p_button;

   @FXML
    TextField u_field;
   @FXML
    TextField n_field;
   @FXML
    TextField p_field;


   static ObservableList<String> items;


   @FXML
    public void initialize (){
       Main.stage.setTitle("Admin");
          users = Main.dummyData.getUsers();


        items = FXCollections.observableArrayList();
        for (User user: users) {
            items.add(user.getUsername());
        }
        user_list.setItems(items);
       user_list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               System.out.println("ListView selection changed from oldValue = "
                       + oldValue + " to newValue = " + newValue);
               u_text.setText("Username: ");
               n_text.setText("Name: ");
               p_text.setText("Password: ");
               username_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getUsername());
               name_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getName());
               password_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getPassword());
               u_button.setVisible(true);
               n_button.setVisible(true);
               p_button.setVisible(true);

           }
       });
    }
    public void u_buttonClicked(){
                if(u_button.getText().equals("Edit")){
                u_button.setText("Ok");
                u_field.setText(username_text.getText());
                u_field.setVisible(true);
                }

            else {

                u_button.setText("Edit");
                users.get(user_list.getSelectionModel().getSelectedIndex()).setUsername(u_field.getText());
                username_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getUsername());
                items.set(user_list.getSelectionModel().getSelectedIndex(),u_field.getText());
                u_field.setVisible(false);

            }
            //TESTIKOODIA
            System.out.println(Main.dummyData.getUsers().get(user_list.getSelectionModel().getSelectedIndex()).getUsername());


    }
    public void n_buttonClicked(){
        if(n_button.getText().equals("Edit")){
            n_button.setText("Ok");
            n_field.setText(name_text.getText());
            n_field.setVisible(true);
        }

        else {

            n_button.setText("Edit");
            users.get(user_list.getSelectionModel().getSelectedIndex()).setName(n_field.getText());
            name_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getName());
            n_field.setVisible(false);

        }
        //TESTIKOODIA
        System.out.println(Main.dummyData.getUsers().get(user_list.getSelectionModel().getSelectedIndex()).getName());


    }
    public void p_buttonClicked(){
        if(p_button.getText().equals("Edit")){
            p_button.setText("Ok");
            p_field.setText(password_text.getText());
            p_field.setVisible(true);
        }

        else {

            p_button.setText("Edit");
            users.get(user_list.getSelectionModel().getSelectedIndex()).setPassword(p_field.getText());
            password_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getPassword());
            p_field.setVisible(false);

        }
        //TESTIKOODIA
        System.out.println(Main.dummyData.getUsers().get(user_list.getSelectionModel().getSelectedIndex()).getPassword());


    }



}
