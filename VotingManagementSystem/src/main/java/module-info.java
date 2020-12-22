module test2FX {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;

    opens sample to javafx.fxml;
    exports sample;
}