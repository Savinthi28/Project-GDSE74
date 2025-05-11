package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.desktop.myfx.myfinalproject.Dto.DailyIncomeDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.DailyIncomeModel;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DailyIncomeController implements Initializable {

    @FXML
    private TableColumn<DailyIncomeDto, Double> colAmount;

    @FXML
    private TableColumn<DailyIncomeDto, String> colDate;

    @FXML
    private TableColumn<DailyIncomeDto, String> colDescription;

    @FXML
    private TableColumn<DailyIncomeDto, Integer> colId;

    @FXML
    private TableColumn<DailyIncomeDto, String> colName;

    @FXML
    private TableView<DailyIncomeDto> tblIncome;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        double amount = Double.parseDouble(txtAmount.getText());
        DailyIncomeDto dailyIncomeDto = new DailyIncomeDto(id,txtName.getText(),txtDate.getText(),txtDescription.getText(),amount);

        try {
            DailyIncomeModel dailyIncomeModel = new DailyIncomeModel();
            boolean isSaved = dailyIncomeModel.saveDailyIncome(dailyIncomeDto);
            if (isSaved) {
                clearFilds();
                new Alert(Alert.AlertType.INFORMATION, "Income has been saved successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to save income").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save income").show();
        }
    }
    private void clearFilds(){
        loadTable();
        txtId.setText("");
        txtName.setText("");
        txtDate.setText("");
        txtDescription.setText("");
        txtAmount.setText("");
    }
    private void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        try {
            DailyIncomeModel dailyIncomeModel = new DailyIncomeModel();
            ArrayList<DailyIncomeDto> dailyIncomeDtos = dailyIncomeModel.viewDailyIncome();
            if (dailyIncomeDtos != null) {
                ObservableList<DailyIncomeDto> data = FXCollections.observableArrayList(dailyIncomeDtos);
                tblIncome.setItems(data);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       loadTable();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}
