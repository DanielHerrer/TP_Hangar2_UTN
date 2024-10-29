module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens entidades to javafx.fxml;
    opens com.utn.hangar to javafx.fxml;
    exports com.utn.hangar;
}