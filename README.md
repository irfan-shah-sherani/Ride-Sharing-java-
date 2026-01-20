## Compile & Run

# for compiling the source code
javac --module-path "lib/javafx-sdk-25.0.1/lib" --add-modules javafx.controls,javafx.fxml src/*.java

# first run the server
java --module-path "lib/javafx-sdk-25.0.1/lib" --add-modules javafx.controls,javafx.fxml -cp src RideServer

# then, run the client in different terminal(passenger)
java --module-path "lib/javafx-sdk-25.0.1/lib" --add-modules javafx.controls,javafx.fxml -cp src Main

# to see the sharing path, again run the client in different terminal


## Project Information

# Group Members:
# 1. Ahmed Nasir Latif ( 4951-FOC/BSCS/F23 [Section: A] )
# 2. Muhammad Irfan ( 4953-FOC/BSCS/F23 [Section: A] )