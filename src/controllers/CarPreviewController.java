package controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Car;
import models.Seat;


public class CarPreviewController {

    private Image seatImage;
    private Image allergicImage;
    private Image wheelchairImage;
    private Image petImage;
    private Image familyImage;

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


        seatImage = new Image("res/seat.png");
        allergicImage = new Image("res/allergy.png");
        wheelchairImage = new Image("res/wheelchair.png");
        petImage = new Image("res/pet.png");
        familyImage = new Image("res/family.png");

        this.car = new Car("new type", 20, true, true, false, true);
        drawCar(seat_grid, car, true);
    }

    static void drawCar(GridPane grid, Car car, boolean previewOnly) {




        Image seatImage = new Image("res/seat.png");
        Image allergicImage = new Image("res/allergy.png");
        Image wheelchairImage = new Image("res/wheelchair.png");
        Image petImage = new Image("res/pet.png");
        Image familyImage = new Image("res/family.png");
        Image reservedImage = new Image("res/cross.png");
        Image selected = new Image("res/selected.png");


        grid.getChildren().clear();
        int row = 0;
        int column = 0;
        int id = 0;

        for (Seat s : car.getSeats()) {
            Button btn = new Button();
            if(s.isFree()) {
                if (s.isFamilyCluster()) {
                    btn.setGraphic(new ImageView(familyImage));
                } else if (s.isForTheAllergic()) {
                    btn.setGraphic(new ImageView(allergicImage));
                } else if (s.isWheelChairAccess()) {
                    btn.setGraphic(new ImageView(wheelchairImage));
                } else if (s.isPetAllowed()) {
                    btn.setGraphic(new ImageView(petImage));
                } else {
                    btn.setGraphic(new ImageView(seatImage));
                }
                if(s.isTemporalReservation()){
                    btn.setGraphic(new ImageView(selected));
                    btn.setDisable(true);
                }
            }
            else {
                btn.setGraphic(new ImageView(reservedImage));
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
                else {
                    btn.setDisable(true);
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

    public void familyClusterSelected() {
        car.setFamilyCluster(family_cluster_box.isSelected());
        drawCar(seat_grid, car, true);

    }

    public void wheelChairSelected() {
        car.setWheelChairAccess(wheelchair_box.isSelected());
        drawCar(seat_grid, car, true);

    }

    public void allergySelected() {
        if (allergy_box.isSelected()) {
            pets_box.setSelected(false);
            car.setPetAllowed(false);
        }
        car.setForTheAllergic(allergy_box.isSelected());
        drawCar(seat_grid, car, true);

    }

    public void petsSelected() {
        if (pets_box.isSelected()) {
            allergy_box.setSelected(false);
            car.setForTheAllergic(false);
        }
        car.setPetAllowed(pets_box.isSelected());
        drawCar(seat_grid, car, true);

    }

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
            AdminController.carTypes_list.add(car.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }
    public void cancelPressed(){
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }

    public void applyClicked(){
        car.setSeatAmount((int)seat_slider.getValue());
        drawCar(seat_grid,car,true);
    }
}
