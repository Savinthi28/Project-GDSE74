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
import lk.ijse.desktop.myfx.myfinalproject.Dto.DailyExpenseDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.DailyExpenseModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DailyExpenseController implements Initializable {

    @FXML
    private TableColumn<DailyExpenseDto, Double> colAmount;

    @FXML
    private TableColumn<DailyExpenseDto, String> colDate;

    @FXML
    private TableColumn<DailyExpenseDto, String> colDescription;

    @FXML
    private TableColumn<DailyExpenseDto, Boolean> colExpense;

    @FXML
    private TableColumn<DailyExpenseDto, Integer> colId;

    @FXML
    private TableView<DailyExpenseDto> tblExpense;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtExpense;

    @FXML
    private TextField txtId;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        try {
            boolean isDelete = new DailyExpenseModel().deleteDailyExpense(new DailyExpenseDto(id));
            if (isDelete) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went error").show();
        }
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        double amount = Double.parseDouble(txtAmount.getText());
        boolean expense = Boolean.parseBoolean(txtExpense.getText());
        DailyExpenseDto dailyExpenseDto = new DailyExpenseDto(id,txtDate.getText(),txtDescription.getText(),amount,expense);

        try {
            DailyExpenseModel dailyExpenseModel = new DailyExpenseModel();
            boolean isSave = dailyExpenseModel.SavedDailyExpense(dailyExpenseDto);
            if (isSave) {
                clearFields();
                new Alert(Alert.AlertType.INFORMATION,"Daily expense has been saved successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Failed to save daily expense").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to add daily expense").show();
        }
    }
    private void clearFields(){
        loadTable();
        txtId.setText("");
        txtDate.setText("");
        txtDescription.setText("");
        txtAmount.setText("");
        txtExpense.setText("");
    }
    private void loadTable(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colExpense.setCellValueFactory(new PropertyValueFactory<>("dailyExpense"));

        try {
            DailyExpenseModel dailyExpenseModel = new DailyExpenseModel();
            ArrayList<DailyExpenseDto> dailyExpenseDtos = dailyExpenseModel.viewAllDailyExpense();
            if (dailyExpenseDtos != null) {
                ObservableList<DailyExpenseDto> observableList = FXCollections.observableArrayList(dailyExpenseDtos);
                tblExpense.setItems(observableList);
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

    public void tableOnClick(MouseEvent mouseEvent) {
        DailyExpenseDto dailyExpenseDto = (DailyExpenseDto) tblExpense.getSelectionModel().getSelectedItem();
        if (dailyExpenseDto != null) {
            txtId.setText(String.valueOf(dailyExpenseDto.getId()));
            txtDate.setText(String.valueOf(dailyExpenseDto.getDate()));
            txtDescription.setText(String.valueOf(dailyExpenseDto.getDescription()));
            txtAmount.setText(String.valueOf(dailyExpenseDto.getAmount()));
            txtExpense.setText(String.valueOf(dailyExpenseDto.isDailyExpense()));
        }
    }
}
