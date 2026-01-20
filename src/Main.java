// package com.example.daaprojectdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        // Show BookRide Panel
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BookRidePanel.fxml"));
        Stage comboStage = new Stage();
        comboStage.setTitle("Book Ride");
        comboStage.setScene(new Scene(loader.load()));
        comboStage.initOwner(primaryStage);
        comboStage.initModality(Modality.APPLICATION_MODAL);
        comboStage.showAndWait();

        BookRideForm controller = loader.getController();
        if (!controller.isSelectionDone()) {
            System.out.println("User closed without selection");
            return;
        }

        // a users selects src and dest on GUI, we return from BookRideFrom
        String source = controller.getSource();
        String destination = controller.getDestination();
        System.out.println("User selected: " + source + " -> " + destination);

        int sourceId = cityToId(source);
        int destinationId = cityToId(destination);

        // Load Graph Scene after user selects src and dest
        FXMLLoader graphLoader = new FXMLLoader(getClass().getResource("City.fxml"));
        Scene graphScene = new Scene(graphLoader.load());
        Stage graphStage = new Stage();
        graphStage.setScene(graphScene);
        graphStage.setTitle("Graph Visualizer");
        graphStage.show();

        Controller graphController = graphLoader.getController();

        // Connect to RideServer (TCP)
        RideClient rideClient;
        try {
            rideClient = new RideClient();
            graphController.setRideClient(rideClient); // pass client to controller
        } catch (Exception e) {
            System.out.println("Unable to connect to server. Continuing without sharing.");
            rideClient = null;
        }

        // Show user's own path and send it to server
        if (sourceId == -1 || destinationId == -1) {
            System.out.println("Unknown source or destination: " + source + " -> " + destination + ". Aborting path computation.");
            return;
        }

        graphController.findPath(sourceId, destinationId);
    }


     private int cityToId(String city) {
        if (city == null) return -1;
        // normalize: remove spaces and non-letters, lowercase
        String key = city.toLowerCase().replaceAll("\\s+", "").replaceAll("[^a-z]", "");
        return switch (key) {
            case "lahore" -> 0;
            case "islamabad" -> 1;
            case "peshawar" -> 2;
            case "quetta" -> 3;
            case "dikhan" -> 4; // accept variants (space removed by normalization)
            case "pindi", "rawalpindi" -> 5;
            case "muzaffarabad" -> 6;
            case "faisalabad", "faislabad" -> 7; // accept common misspelling
            case "lyari" -> 8;
            default -> -1;
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
