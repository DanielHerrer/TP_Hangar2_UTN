module com.example.tp_hangar2_utn {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.tp_hangar2_utn to javafx.fxml;
    exports com.example.tp_hangar2_utn;
}