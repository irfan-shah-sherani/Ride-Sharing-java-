## Compile & Run

```bash
javac --module-path "lib/javafx-sdk-20/lib" --add-modules javafx.controls,javafx.fxml src/*.java
java --module-path "lib/javafx-sdk-20/lib" --add-modules javafx.controls,javafx.fxml -cp src Main
