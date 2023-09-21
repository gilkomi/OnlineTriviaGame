module com.example.mmn16q1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.mmn16q1 to javafx.fxml;
    exports com.example.mmn16q1;
}