module com.app.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.app.library to javafx.fxml;
    exports com.app.library;
}