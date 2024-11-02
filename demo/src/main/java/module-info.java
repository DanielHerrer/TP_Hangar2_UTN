module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;


    opens com.utn.hangar to javafx.fxml;
    exports com.utn.hangar;
}