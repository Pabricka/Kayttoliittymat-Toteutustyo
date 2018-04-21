package controllers;

import models.Journey;
import models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;


public class AdminController {
    ArrayList<User> users;
    ArrayList<Journey> journeys;

   @FXML
    ListView<String> user_list;
   @FXML
    ListView<String> journey_list;

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

   @FXML
    ComboBox<String> sort_box;



   static ObservableList<String> items;
   static  ObservableList<String>  journey_items;


   @FXML
    public void initialize (){


       sort_box.getItems().removeAll(sort_box.getItems());
       sort_box.getItems().addAll("user","date","connection");
       sort_box.valueProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               if(sort_box.getSelectionModel().getSelectedItem() == "connection") {

                   Collections.sort(journeys,(o1, o2) -> o1.getConnection().getFrom().toString().compareTo(o2.getConnection().getFrom().toString())
                   );
                   journey_items = FXCollections.observableArrayList();
                   for (Journey journey : journeys) {
                       journey_items.add(journey.getStrings());
                   }

                   journey_list.setItems(journey_items);
               }


               else if (sort_box.getSelectionModel().getSelectedItem() == "user"){


                       Collections.sort(journeys, (o1, o2) -> o1.getBuyer().getName().compareTo(o2.getBuyer().getName())
                       );
                       journey_items = FXCollections.observableArrayList();
                       for (Journey journey : journeys) {
                           journey_items.add(journey.getStrings());
                       }

                   journey_list.setItems(journey_items);

                   }
                   else {


                   Collections.sort(journeys, (o1, o2) -> o1.getDate().compareTo(o2.getDate())
                   );
                   journey_items = FXCollections.observableArrayList();
                   for (Journey journey : journeys) {
                       journey_items.add(journey.getStrings());
                   }

                   journey_list.setItems(journey_items);

               }
               }

               /*else{
                   for (String s:journey_items){
                       System.out.println(s);
                   }
                   System.out.println();
                   Collections.sort(journeys, (o1, o2) -> o1.getConnection().getFrom().compareTo(o2.getConnection().getFrom())
                   );
                   journey_items = FXCollections.observableArrayList();
                   for (Journey journey:journeys){
                       journey_items.add(journey.getStrings());
                   }
                   for (String s:journey_items){
                       System.out.println(s);
                   }


               }*/

       });



       try {
           users = Client.dummyData.getUsers();
           journeys = Client.dummyData.getJourneys();
       }
       catch (Exception e){
           e.printStackTrace();
       }


        items = FXCollections.observableArrayList();
        journey_items = FXCollections.observableArrayList();
        for (User user: users) {
            items.add(user.getUsername());
        }
        for (Journey journey:journeys){
            journey_items.add(journey.getStrings());
        }
        user_list.setItems(items);
       journey_list.setItems(journey_items);

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
       }
       );

    }
    public void u_buttonClicked(){

        if(u_button.getText().equals("Edit")){
            u_button.setText("Ok");
            u_field.setText(username_text.getText());
            u_field.setVisible(true);
        }

        else {
            u_button.setText("Edit");

            //change the users username in database
            try {
                Client.dummyData.changeUsername(user_list.getSelectionModel().getSelectedIndex(), u_field.getText());
                users = Client.dummyData.getUsers();
            }
            catch (Exception e){
                e.printStackTrace();
            }

            username_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getUsername());
            items.set(user_list.getSelectionModel().getSelectedIndex(),u_field.getText());
            u_field.setVisible(false);

            }



    }
    public void n_buttonClicked(){
        if(n_button.getText().equals("Edit")){
            n_button.setText("Ok");
            n_field.setText(name_text.getText());
            n_field.setVisible(true);
        }

        else {

            n_button.setText("Edit");

            //change the users name in database
            try {
                Client.dummyData.changeName(user_list.getSelectionModel().getSelectedIndex(), n_field.getText());
                users = Client.dummyData.getUsers();
            }
            catch (Exception e){
                e.printStackTrace();
            }

            name_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getName());
            n_field.setVisible(false);

        }



    }
    public void p_buttonClicked(){
        if(p_button.getText().equals("Edit")){
            p_button.setText("Ok");
            p_field.setText(password_text.getText());
            p_field.setVisible(true);
        }

        else {

            p_button.setText("Edit");
            //change the users password in database
            try {
                Client.dummyData.changePassword(user_list.getSelectionModel().getSelectedIndex(), p_field.getText());
                users = Client.dummyData.getUsers();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            password_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getPassword());
            p_field.setVisible(false);

        }


    }



}
