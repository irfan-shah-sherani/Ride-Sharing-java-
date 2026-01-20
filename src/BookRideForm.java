// package com.example.daaprojectdemo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class BookRideForm {

    static String source;
    static String destination;
    private boolean selectionDone = false;

    @FXML
    public ComboBox<String> sourceCombo;

    @FXML
    public ComboBox<String> destinationCombo;

    @FXML
    private Label statusLabel;
    public Button chooseOnMap;
    public Button confirmRideButton;

    @FXML
    public void initialize() {
        sourceCombo.getItems().addAll(
                "Islamabad", "Rawalpindi", "Lahore", "Lyari", "Peshawar", "Muzaffarabad", "Quetta", "Faisalabad", "DIKhan"
        );

        destinationCombo.getItems().addAll(
                "Islamabad", "Rawalpindi", "Lahore", "Lyari", "Peshawar", "Muzaffarabad", "Quetta", "Faisalabad", "DIKhan"
        );
    }


    public void clickChooseOnMap() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CitySelector.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Choose From Map");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait(); // BLOCK until user closes the window

            // After window closes, get selected cities
            RideFormUsingMapController mapController = loader.getController();
            if (mapController.isSelectionDone()) {
                sourceCombo.setValue(mapController.getSourceCity());
                destinationCombo.setValue(mapController.getDestinationCity());

                statusLabel.setText(
                        "Selected from map: " + sourceCombo.getValue() + " -> " + destinationCombo.getValue()
                );

                // You can now call findPath or other methods here
                BookRideForm.source = sourceCombo.getValue();
                BookRideForm.destination = destinationCombo.getValue();
                System.out.println("Source" + source + "Dest" + destination);
            }
//            stage = (Stage) confirmRideButton.getScene().getWindow();
//            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void clickConfirmRide() {

        source = sourceCombo.getValue();
        destination = destinationCombo.getValue();

        if (source == null || destination == null) {
            statusLabel.setText("Please select both source and destination");
            return;
        }

        statusLabel.setText("Ride booked from " + source + " to " + destination);

        selectionDone = true;
        System.out.println(selectionDone);


        System.out.println("source: " + source + ", destination: " + destination);
        Stage stage = (Stage) confirmRideButton.getScene().getWindow();
        stage.close();


    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public boolean isSelectionDone() {
        return selectionDone;
    }

}


