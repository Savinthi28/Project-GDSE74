package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ReportsController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colGenerateBy;

    @FXML
    private TableColumn<?, ?> colReportId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableView<?> tblReports;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtGenerateBy;

    @FXML
    private TextField txtReportId;

    @FXML
    private TextField txtType;

    @FXML
    private TextField txtUserId;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}
