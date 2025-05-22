package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.desktop.myfx.myfinalproject.Dto.DailyExpenseDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.DailyExpenseModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DailyExpenseController implements Initializable {
    public AnchorPane getAncDailyExpense(){
        return null;
    }

    @FXML
    private AnchorPane ancDailyExpense;
    private String path;

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
    private ComboBox<Boolean> comExpense;

    @FXML
    private Label lblId;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
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

    private void loadNextId () throws SQLException {
        DailyExpenseModel dailyExpenseModel = new DailyExpenseModel();
        String id = dailyExpenseModel.getNextId();
        lblId.setText(id);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
        double amount = Double.parseDouble(txtAmount.getText());
        boolean expense = Boolean.parseBoolean(String.valueOf(comExpense.getValue()));
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
        lblId.setText("");
        txtDate.setText("");
        txtDescription.setText("");
        txtAmount.setText("");
        comExpense.setValue(Boolean.valueOf(""));
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
        try {
            loadNextId();
            loadExpense();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }

    private void loadExpense() throws SQLException {
        ArrayList<Boolean> expense = DailyExpenseModel.getAllExpense();
        ObservableList<Boolean> observableList = FXCollections.observableArrayList(expense);
        observableList.addAll(expense);
        comExpense.setItems(observableList);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
        double amount = Double.parseDouble(txtAmount.getText());
        boolean expense = Boolean.parseBoolean(String.valueOf(comExpense.getValue()));
        DailyExpenseDto dailyExpenseDto = new DailyExpenseDto(id,txtDate.getText(),txtDescription.getText(),amount,expense);
        try {
            boolean isSave = DailyExpenseModel.updateDailyExpense(dailyExpenseDto);
            if (isSave) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION,"Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Failed to update daily expense").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to add daily expense").show();
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        DailyExpenseDto dailyExpenseDto = (DailyExpenseDto) tblExpense.getSelectionModel().getSelectedItem();
        if (dailyExpenseDto != null) {
            lblId.setText(String.valueOf(dailyExpenseDto.getId()));
            txtDate.setText(String.valueOf(dailyExpenseDto.getDate()));
            txtDescription.setText(String.valueOf(dailyExpenseDto.getDescription()));
            txtAmount.setText(String.valueOf(dailyExpenseDto.getAmount()));
            comExpense.setValue(Boolean.valueOf(String.valueOf(dailyExpenseDto.isDailyExpense())));
        }
    }

    public void btnGoToIncomeOnAction(ActionEvent actionEvent) {
        navigateTo("/View/DailyIncomeView.fxml");
    }

    private <Sring> void navigateTo(Sring path){
        try {
            ancDailyExpense.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource((String) path));

            anchorPane.prefWidthProperty().bind(ancDailyExpense.widthProperty());
            anchorPane.prefHeightProperty().bind(ancDailyExpense.heightProperty());
            ancDailyExpense.getChildren().add(anchorPane);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();

        }
    }

    public void btnGoToExpenseOnAction(ActionEvent actionEvent) {
        navigateTo("/View/DailyExpenseView.fxml");
    }

    public void comExpenseOnAction(ActionEvent actionEvent) {
        Boolean selectedExpense = (Boolean) comExpense.getSelectionModel().getSelectedItem();
        System.out.println(selectedExpense);
    }
}
