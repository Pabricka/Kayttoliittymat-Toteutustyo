package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Car;
import models.Seat;


public class CarPreviewController {


    private Car car;

    @FXML
    private CheckBox family_cluster_box, wheelchair_box, allergy_box, pets_box;

    @FXML
    GridPane seat_grid;

    @FXML
    private Button ok;

    @FXML
    private TextField name_field;

    @FXML
    private Slider seat_slider;

    @FXML
    private Text error_msg;

    @FXML
    public void initialize() {
        family_cluster_box.setSelected(true);
        wheelchair_box.setSelected(true);
        allergy_box.setSelected(true);
        pets_box.setSelected(false);

        error_msg.setText("");



        this.car = new Car("new type", 20, true, true, false, true);
        drawCar(seat_grid, car, true, true,true,true,true);
    }

    /**
     * Draws a car as buttons with selected features to a GridPane
     * @param grid GridPane where seats are drawn
     * @param car to be drawn
     * @param previewOnly to disable or not to disable all buttons
     * @param allergic disable seats for the allergic or not
     * @param wheelchair disable seats with wheelchair access or not
     * @param pets disable pet seats or not
     * @param family disable family clusters or not
     */
    static void drawCar(GridPane grid, Car car, boolean previewOnly, boolean allergic, boolean wheelchair, boolean pets, boolean family) {


        //images to be used in buttons
        Image seatImage = new Image("res/seat.png");
        Image allergicImage = new Image("res/allergy.png");
        Image wheelchairImage = new Image("res/wheelchair.png");
        Image petImage = new Image("res/pet.png");
        Image familyImage = new Image("res/family.png");
        Image reservedImage = new Image("res/cross.png");
        Image selected = new Image("res/selected.png");


        //clear the grid before drawin
        grid.getChildren().clear();
        int row = 0;
        int column = 0;
        int id = 0;

        //generate a button with correct image for every seat
        for (Seat s : car.getSeats()) {
            Button btn = new Button();
            if(s.isFree()) {
                if (s.isFamilyCluster()) {
                    btn.setGraphic(new ImageView(familyImage));
                    if(!family){
                        btn.setDisable(true);
                    }
                } else if (s.isForTheAllergic()) {
                    btn.setGraphic(new ImageView(allergicImage));
                    if(!allergic){
                        btn.setDisable(true);
                    }
                } else if (s.isWheelChairAccess()) {
                    btn.setGraphic(new ImageView(wheelchairImage));
                    if(!wheelchair){
                        btn.setDisable(true);
                    }
                } else if (s.isPetAllowed()) {
                    btn.setGraphic(new ImageView(petImage));
                    if(!pets){
                        btn.setDisable(true);
                    }
                } else {
                    btn.setGraphic(new ImageView(seatImage));
                    if(!allergic||!family||!pets||!wheelchair){
                        btn.setDisable(true);

                    }
                }
                if(s.isTemporalReservation()){
                    btn.setGraphic(new ImageView(selected));
                    btn.setDisable(true);
                }
            }
            else {
                btn.setGraphic(new ImageView(reservedImage));
                btn.setDisable(true);
            }
            if (previewOnly) {
                btn.setDisable(true);
            }
            else {

                if(s.isFree()){
                    btn.setId(Integer.toString(id));
                    btn.setOnMouseClicked(event -> {
                        if(Client.session.getPassengers() > Client.session.getSelectedSeats().size()) {
                            btn.setGraphic(new ImageView(selected));
                            Client.session.addSelectedSeat(Integer.parseInt(btn.getId()));
                            Client.session.addSelectedCar(Integer.parseInt(btn.getId()));
                            s.setTemporalReservation(true);
                            btn.setDisable(true);
                        }
                    });
                }
                id++;
            }
            grid.add(btn, row, column);
            if (row == 1) {
                row++;
            }
            row++;
            if (row == 5) {
                row = 0;
                column++;
            }


        }

    }

    /**
     * adds family seats
     */
    public void familyClusterSelected() {
        car.setFamilyCluster(family_cluster_box.isSelected());
        drawCar(seat_grid, car, true,true,true,true,true);

    }
    /**
     * adds seats with wheelchair access
     */
    public void wheelChairSelected() {
        car.setWheelChairAccess(wheelchair_box.isSelected());
        drawCar(seat_grid, car, true,true,true,true,true);

    }

    /**
     * adds allergy friendly seats
     */
    public void allergySelected() {
        if (allergy_box.isSelected()) {
            pets_box.setSelected(false);
            car.setPetAllowed(false);
        }
        car.setForTheAllergic(allergy_box.isSelected());
        drawCar(seat_grid, car, true,true,true,true,true);

    }

    /**
     * adds pet seats
     */
    public void petsSelected() {
        if (pets_box.isSelected()) {
            allergy_box.setSelected(false);
            car.setForTheAllergic(false);
        }
        car.setPetAllowed(pets_box.isSelected());
        drawCar(seat_grid, car, true,true,true,true,true);

    }

    /**
     * checks that the name is OK, and if so adds the new car type to the database
     * before closing the window
     */
    public void okPressed() {
        String tmp = name_field.getText();
        if(tmp.length()==0 ||tmp.length()>15){
            error_msg.setText("Name must be between 0-16" + System.lineSeparator() + "characters long");
            return;
        }

        car.setName(name_field.getText());
        try {
            Client.dummyData.addCarType(car);
            AdminController.carTypes = Client.dummyData.getCarTypes();
            AdminController.carTypes_list.add(AdminController.carTypes_list.size()-1,car.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    /**
     * goes back without doing anything
     */
    public void cancelPressed(){
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    /**
     * checks the value of seat slider and updates the preview car
     */
    public void applyClicked(){
        car.setSeatAmount((int)seat_slider.getValue());
        drawCar(seat_grid, car, true,true,true,true,true);
    }
}
