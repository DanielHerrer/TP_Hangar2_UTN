module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;
    requires jdk.jdi;

    opens entidades to javafx.fxml;
    opens com.utn.hangar to javafx.fxml;
    exports com.utn.hangar;
    exports com.utn.hangar.invitadoControllers;
    opens com.utn.hangar.invitadoControllers to javafx.fxml;
    exports com.utn.hangar.adminControllers;
    opens com.utn.hangar.adminControllers to javafx.fxml;
    exports com.utn.hangar.homeControllers;
    opens com.utn.hangar.homeControllers to javafx.fxml;
    exports com.utn.hangar.operadorControllers;
    opens com.utn.hangar.operadorControllers to javafx.fxml;
}