package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.desktop.myfx.myfinalproject.Dto.ReportsDto;
import lk.ijse.desktop.myfx.myfinalproject.Dto.UserDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.ReportsModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadNextId();
            loadUserId();
            clearFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }

    private void loadUserId() throws SQLException {
        ArrayList<String> userIds = ReportsModel.getAllUserId();
        ObservableList<String> users = FXCollections.observableArrayList(userIds);
        users.addAll(userIds);
        comUserId.setItems(users);
    }

    @FXML
    private TableColumn<ReportsDto, String> colDate;

    @FXML
    private TableColumn<ReportsDto, String> colGenerateBy;

    @FXML
    private TableColumn<ReportsDto, String> colReportId;

    @FXML
    private TableColumn<ReportsDto, String> colType;

    @FXML
    private TableColumn<ReportsDto, String> colUserId;

    @FXML
    private TableView<ReportsDto> tblReports;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtGenerateBy;

    @FXML
    private Label lblId;

    @FXML
    private TextField txtType;

    @FXML
    private ComboBox<String> comUserId;

    @FXML
    void btnClearOnAction(ActionEvent event) throws SQLException {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        String idToDelete = lblId.getText();
        if (idToDelete == null || idToDelete.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select a Report from the table or enter an ID to delete.").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Report");
        alert.setContentText("Are you sure you want to delete this report?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isDeleted = ReportsModel.deleteReports(idToDelete);
                if (isDeleted) {
                    clearFields();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Report Deleted Successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete Report.").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error deleting Report: " + e.getMessage()).show();
            }
        }
    }

    private void loadNextId() throws SQLException {
        ReportsModel reportsModel = new ReportsModel();
        String id = reportsModel.getNextId();
        lblId.setText(id);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        ReportsDto reportsDto = new ReportsDto(lblId.getText(),txtDate.getText(),comUserId.getValue(),txtType.getText(),txtGenerateBy.getText());

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
    private void clearFields() throws SQLException {
        loadTable();
        lblId.setText("");
        txtDate.setText("");
        comUserId.setValue("");
        txtType.setText("");
        txtGenerateBy.setText("");

        loadNextId();
        Platform.runLater(()-> {
            lblId.setText(lblId.getText());
        });
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
    public void btnUpdateOnAction(ActionEvent event) {
        ReportsDto reportsDto = new ReportsDto(lblId.getText(), txtDate.getText(), comUserId.getValue(), txtType.getText(), txtGenerateBy.getText());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Update Report");
        alert.setContentText("Are you sure you want to update this report?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isSave = ReportsModel.updateReports(reportsDto);
                if (isSave) {
                    clearFields();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Report has been updated successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Report could not be updated").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Report could not be updated").show();
            }
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        ReportsDto reportsDto = (ReportsDto) tblReports.getSelectionModel().getSelectedItem();
        if (reportsDto != null) {
            lblId.setText(reportsDto.getReportId());
            txtDate.setText(String.valueOf(reportsDto.getDate()));
            comUserId.setValue(reportsDto.getUserId());
            txtType.setText(String.valueOf(reportsDto.getReportType()));
            txtGenerateBy.setText(String.valueOf(reportsDto.getGenerateBy()));
        }
    }

    public void comUserIdOnAction(ActionEvent actionEvent) {
        String selectedUserId = (String) comUserId.getSelectionModel().getSelectedItem();
        System.out.println(selectedUserId);
    }
}
