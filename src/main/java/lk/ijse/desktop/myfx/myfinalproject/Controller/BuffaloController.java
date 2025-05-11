package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.desktop.myfx.myfinalproject.Dto.BuffaloDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.BuffaloModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class BuffaloController implements Initializable {

    @FXML
    private TableColumn<BuffaloDto, Integer> colAge;

    @FXML
    private TableColumn<BuffaloDto, String> colGender;

    @FXML
    private TableColumn<BuffaloDto, String> colHealth;

    @FXML
    private TableColumn<BuffaloDto, String> colID;

    @FXML
    private TableColumn<BuffaloDto, Double> colMilk;

    @FXML
    private TableView<BuffaloDto> tblBuffalo;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtBuffaloID;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtHealth;

    @FXML
    private TextField txtMilkProduction;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
   public void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        int age = Integer.parseInt(txtAge.getText());
        double milkProduction = Double.parseDouble(txtMilkProduction.getText());
        BuffaloDto buffaloDto = new BuffaloDto(txtBuffaloID.getText(),milkProduction, txtGender.getText(), age, txtHealth.getText());

        try {
            BuffaloModel buffaloModel = new BuffaloModel();
            boolean isSave = buffaloModel.saveBuffalo(buffaloDto);
            if (isSave) {
               clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Buffalo has been saved successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to save buffalo").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save buffalo").show();
        }
    }
    private void clearFields() {
        loadTable();
        txtAge.setText("");
        txtMilkProduction.setText("");
        txtGender.setText("");
        txtBuffaloID.setText("");
        txtHealth.setText("");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       loadTable();

    }
private void loadTable() {
        colID.setCellValueFactory(new PropertyValueFactory<>("buffaloID"));
        colMilk.setCellValueFactory(new PropertyValueFactory<>("milkProduction"));
    colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
    colHealth.setCellValueFactory(new PropertyValueFactory<>("healthStatus"));


    try {
            BuffaloModel buffaloModel = new BuffaloModel();
            ArrayList<BuffaloDto> buffaloDtos = buffaloModel.viewAllBuffalo();
            if (buffaloDtos != null) {
                ObservableList<BuffaloDto> data = FXCollections.observableArrayList(buffaloDtos);
                tblBuffalo.setItems(data);
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

}
