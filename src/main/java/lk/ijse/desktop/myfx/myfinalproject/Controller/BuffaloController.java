package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.desktop.myfx.myfinalproject.Dto.BuffaloDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.BuffaloModel;
import lombok.Getter;

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
    private Label lblId;

    @FXML
    private ComboBox<String> comGender;

    @FXML
    private TextField txtHealth;

    @FXML
    private TextField txtMilkProduction;

    private final String milkPattern = "^\\d+(\\.\\d+)?$";
    private final String agePattern = "^\\d+$";
    private final String healthPattern = "^[A-Za-z ]+$";

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        String id = lblId.getText();
        try {
            boolean isDelete = new BuffaloModel().deleteBuffalo(new BuffaloDto(id));
            if (isDelete) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Buffalo Deleted").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Buffalo Not Deleted").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error").show();
        }
    }

    private void loadNextId () throws SQLException{
        BuffaloModel buffaloModel = new BuffaloModel();
        String nextId = buffaloModel.getNextId();
        lblId.setText(nextId);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        int age = Integer.parseInt(txtAge.getText());
        double milkProduction = Double.parseDouble(txtMilkProduction.getText());
        BuffaloDto buffaloDto = new BuffaloDto(lblId.getText(), milkProduction, comGender.getValue(), age, txtHealth.getText());

        String milk = txtMilkProduction.getText();
        String ageString = txtAge.getText();
        String health = txtHealth.getText();

        boolean isValidMilk = milk.matches(milkPattern);
        boolean isValidAge = ageString.matches(agePattern);
        boolean isValidHealth = health.matches(healthPattern);

        if (isValidMilk &&  isValidAge && isValidHealth) {
            try {
                BuffaloModel buffaloModel = new BuffaloModel();
                boolean isSave = buffaloModel.saveBuffalo(buffaloDto);
                if (isSave) {
                    clearFields();
                    new Alert(Alert.AlertType.INFORMATION, "Buffalo has been saved successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save buffalo").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to save buffalo").show();
            }
        }
    }
    private void clearFields() {
        loadTable();
        txtAge.setText("");
        txtMilkProduction.setText("");
        comGender.setValue("");
        lblId.setText("");
        txtHealth.setText("");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadNextId();
            loadBuffaloGender();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }
    private void loadBuffaloGender() throws SQLException {
        ArrayList<String> genderList = BuffaloModel.getAllBuffaloGender();
        ObservableList<String> genderObservableList = FXCollections.observableArrayList(genderList);
        genderObservableList.addAll(genderList);
        comGender.setItems(genderObservableList);
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
    public void btnUpdateOnAction(ActionEvent event) {
        double milkProduction = Double.parseDouble(txtMilkProduction.getText());
        int age = Integer.parseInt(txtAge.getText());
        BuffaloDto buffaloDto = new BuffaloDto(lblId.getText(), milkProduction, comGender.getValue(), age, txtHealth.getText());

        String milk = txtMilkProduction.getText();
        String ageString = txtAge.getText();
        String health = txtHealth.getText();

        boolean isValidMilk = milk.matches(milkPattern);
        boolean isValidAge = ageString.matches(agePattern);
        boolean isValidHealth = health.matches(healthPattern);
        if (isValidMilk && isValidAge && isValidHealth) {
            try {
                boolean isSave = BuffaloModel.updateFarmer(buffaloDto);
                if (isSave) {
                    clearFields();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Buffalo has been updated successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update buffalo").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to save buffalo").show();
            }
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        BuffaloDto buffaloDto = (BuffaloDto) tblBuffalo.getSelectionModel().getSelectedItem();
        if (buffaloDto != null) {
            lblId.setText(buffaloDto.getBuffaloID());
            txtMilkProduction.setText(String.valueOf(buffaloDto.getMilkProduction()));
            comGender.setValue(buffaloDto.getGender());
            txtAge.setText(String.valueOf(buffaloDto.getAge()));
            txtHealth.setText(String.valueOf(buffaloDto.getHealthStatus()));
        }
    }

    public void txtMilkChange(KeyEvent keyEvent) {
        String milk = txtMilkProduction.getText();
        boolean isValidMilk = milk.matches(milkPattern);
        if (isValidMilk) {
            txtMilkProduction.setStyle(txtMilkProduction.getStyle()+ ";-fx-border-color: blue");
        }else {
            txtMilkProduction.setStyle(txtMilkProduction.getStyle()+ ";-fx-border-color: red");
        }
    }

    public void txtAgeChange(KeyEvent keyEvent) {
        String age = txtAge.getText();
        boolean isValidAge = age.matches(agePattern);
        if (isValidAge) {
            txtAge.setStyle(txtAge.getStyle()+ ";-fx-border-color: blue");
        }else {
            txtAge.setStyle(txtAge.getStyle()+ ";-fx-border-color: red");
        }
    }

    public void txtHealthChange(KeyEvent keyEvent) {
        String health = txtHealth.getText();
        boolean isValidHealth = health.matches(healthPattern);
        if (isValidHealth) {
            txtHealth.setStyle(txtHealth.getStyle()+ ";-fx-border-color: blue");
        }else {
            txtHealth.setStyle(txtHealth.getStyle()+ ";-fx-border-color: red");
        }
    }

    public void comGenderOnAction(ActionEvent actionEvent) {
        String selectedGender = (String) comGender.getSelectionModel().getSelectedItem();
        System.out.println(selectedGender);
    }
}
