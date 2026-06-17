module com.pucpr.demo1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.capitulozero to javafx.fxml;
    exports com.capitulozero;
}