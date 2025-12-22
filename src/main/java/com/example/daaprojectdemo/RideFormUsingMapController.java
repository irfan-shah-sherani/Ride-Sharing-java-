package com.example.daaprojectdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RideFormUsingMapController {

    @FXML
    public Button LahoreButton, IslamabadButton, LyariButton,
            FaisalabadButton, MuzaffarabadButton, PeshawarButton, PindiButton;

    @FXML
    public Label statusLabel;

    private String sourceCity = null;
    private String destinationCity = null;

    private boolean selectionDone = false;

    @FXML
    public void citySelected(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String city = clickedButton.getText();

        if (sourceCity == null) {
            sourceCity = city;
            clickedButton.setDisable(true);
            statusLabel.setText("Selected source: " + sourceCity);
        } else if (destinationCity == null) {
            destinationCity = city;
            clickedButton.setDisable(true);
            statusLabel.setText("Selected destination: " + destinationCity);
            selectionDone = true;

            // Close window automatically when selection complete
            Stage stage = (Stage) clickedButton.getScene().getWindow();
            stage.close();
        } else {
            statusLabel.setText("Source and Destination already selected");
        }
    }

    // Getters to read selected cities
    public String getSourceCity() {
        return sourceCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public boolean isSelectionDone() {
        return selectionDone;
    }
}
