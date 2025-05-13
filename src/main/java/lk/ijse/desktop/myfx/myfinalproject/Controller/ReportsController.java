package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.desktop.myfx.myfinalproject.Dto.ReportsDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.ReportsModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }

    @FXML
    private TableColumn<ReportsDto, String> colDate;

    @FXML
    private TableColumn<ReportsDto, String> colGenerateBy;

    @FXML
    private TableColumn<ReportsDto, Integer> colReportId;

    @FXML
    private TableColumn<ReportsDto, String> colType;

    @FXML
    private TableColumn<ReportsDto, Integer> colUserId;

    @FXML
    private TableView<ReportsDto> tblReports;

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
    public void btnDeleteOnAction(ActionEvent event)  {
        int reportId = Integer.parseInt(txtReportId.getText());
        try {
            boolean isDelete = new ReportsModel().deleteReports(new ReportsDto(reportId));
            if (isDelete) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Report Deleted").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Report Not Deleted").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Report Not Deleted").show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        int reportId = Integer.parseInt(txtReportId.getText());
        int userId = Integer.parseInt(txtUserId.getText());
        ReportsDto reportsDto = new ReportsDto(reportId,txtDate.getText(),userId,txtType.getText(),txtGenerateBy.getText());

        try {
            ReportsModel reportsModel = new ReportsModel();
            boolean isSaved = reportsModel.saveReport(reportsDto);
            if (isSaved) {
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Report has been saved successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Report could not be saved").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Report could not be saved").show();
        }
    }
    private void clearFields(){
        loadTable();
        txtReportId.setText("");
        txtDate.setText("");
        txtUserId.setText("");
        txtType.setText("");
        txtGenerateBy.setText("");
    }
    private void loadTable() {
        colReportId.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("reportType"));
        colGenerateBy.setCellValueFactory(new PropertyValueFactory<>("generateBy"));

        try {
            ReportsModel reportsModel = new ReportsModel();
            ArrayList<ReportsDto> reportsDtos = reportsModel.viewAllReports();
            if (reportsDtos != null) {
                ObservableList<ReportsDto> List = FXCollections.observableArrayList(reportsDtos);
                tblReports.setItems(List);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    public void tableOnClick(MouseEvent mouseEvent) {
        ReportsDto reportsDto = (ReportsDto) tblReports.getSelectionModel().getSelectedItem();
        if (reportsDto != null) {
            txtReportId.setText(String.valueOf(reportsDto.getReportId()));
            txtDate.setText(String.valueOf(reportsDto.getDate()));
            txtUserId.setText(String.valueOf(reportsDto.getUserId()));
            txtType.setText(String.valueOf(reportsDto.getReportType()));
            txtGenerateBy.setText(String.valueOf(reportsDto.getGenerateBy()));
        }
    }
}
