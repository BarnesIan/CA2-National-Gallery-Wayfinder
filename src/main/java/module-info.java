module com.example.ca2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.ca2 to javafx.fxml;
    exports com.example.ca2;
    exports dijkstra;
    opens dijkstra to javafx.fxml;
}