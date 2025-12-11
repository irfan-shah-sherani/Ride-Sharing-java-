import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("City.fxml"));
        Scene scene = new Scene(loader.load());

        Controller controller = loader.getController();

        stage.setTitle("Graph Shortest Path Visualizer");
        stage.setScene(scene);
        stage.show();

        // ---- RUN THE ANIMATIONS IN ORDER ----

        // 1) Run path 2 → 8
        controller.findPath(2, 8);

        // 2) After 3 sec, run path 2 → 3
        PauseTransition d1 = new PauseTransition(Duration.seconds(3));
        d1.setOnFinished(e -> controller.findPath(2, 3));
        d1.play();

        // 3) After another 3 sec, run path 2 → 8 again
        PauseTransition d2 = new PauseTransition(Duration.seconds(6));
        d2.setOnFinished(e -> controller.findPath(2, 8));
        d2.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
