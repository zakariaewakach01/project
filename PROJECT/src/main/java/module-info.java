module com.example.pfa {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pfa to javafx.fxml;
    exports com.example.pfa;
}