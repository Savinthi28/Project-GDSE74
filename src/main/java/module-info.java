module lk.ijse.desktop.myfx.myfinalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;


    opens lk.ijse.desktop.myfx.myfinalproject.Controller to javafx.fxml;
    exports lk.ijse.desktop.myfx.myfinalproject;
    opens lk.ijse.desktop.myfx.myfinalproject.Dto to javafx.base;

}