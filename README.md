## Compile & Run

javac --module-path "lib/javafx-sdk-25.0.1/lib" --add-modules javafx.controls,javafx.fxml src/*.java

java --module-path "lib/javafx-sdk-25.0.1/lib" --add-modules javafx.controls,javafx.fxml -cp src RideServer

java --module-path "lib/javafx-sdk-25.0.1/lib" --add-modules javafx.controls,javafx.fxml -cp src Main
