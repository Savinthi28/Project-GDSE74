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
import lk.ijse.desktop.myfx.myfinalproject.Dto.MilkStorageDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.MilkStorageModel;

import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MilkStorageController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }

    @FXML
    private TableColumn<MilkStorageDto, Integer> colCollectionId;

    @FXML
    private TableColumn<MilkStorageDto, String> colDate;

    @FXML
    private TableColumn<MilkStorageDto, Time> colDuration;

    @FXML
    private TableColumn<MilkStorageDto, Integer> colStorageId;

    @FXML
    private TableColumn<MilkStorageDto, Double> colTemperature;

    @FXML
    private TableView<MilkStorageDto> tblMilkStorage;

    @FXML
    private TextField txtCollectionId;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtStorageId;

    @FXML
    private TextField txtTemperature;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearField();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtCollectionId.getText());
        try {
            boolean isDelete = new MilkStorageModel().deleteMilkStorage(new MilkStorageDto(id));
            if (isDelete) {
                clearField();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Milk Storage Deleted Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Milk Storage Deletion Failed").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Milk Storage Deletion Failed").show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        int storageId = Integer.parseInt(txtStorageId.getText());
        int collectionId = Integer.parseInt(txtCollectionId.getText());
        Time duration = Time.valueOf(txtDuration.getText());
        double temperature = Double.parseDouble(txtTemperature.getText());
        MilkStorageDto milkStorageDto = new MilkStorageDto(storageId,collectionId,txtDate.getText(),duration,temperature);

        try {
            MilkStorageModel milkStorageModel = new MilkStorageModel();
            boolean isSave = milkStorageModel.saveMilkStorage(milkStorageDto);
            if (isSave) {
                clearField();
                new Alert(Alert.AlertType.INFORMATION, "Milk Storage has been saved successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Milk Storage has not been saved").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Milk Storage has not been saved").show();
        }
    }
private void clearField() {
        loadTable();
        txtStorageId.setText("");
        txtCollectionId.setText("");
        txtDate.setText("");
        txtDuration.setText("");
        txtTemperature.setText("");
}
private void loadTable() {
        colStorageId.setCellValueFactory(new PropertyValueFactory<>("storageId"));
        colCollectionId.setCellValueFactory(new PropertyValueFactory<>("collectionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colTemperature.setCellValueFactory(new PropertyValueFactory<>("temperature"));

        try {
            MilkStorageModel milkStorageModel = new MilkStorageModel();
            ArrayList<MilkStorageDto> milkStorageDtos = milkStorageModel.viewAllMilkStorage();
            if (milkStorageDtos != null) {
                ObservableList<MilkStorageDto> list = FXCollections.observableArrayList(milkStorageDtos);
                tblMilkStorage.setItems(list);
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
        MilkStorageDto milkStorageDto = (MilkStorageDto) tblMilkStorage.getSelectionModel().getSelectedItem();
        if (milkStorageDto != null) {
            txtStorageId.setText(String.valueOf(milkStorageDto.getStorageId()));
            txtCollectionId.setText(String.valueOf(milkStorageDto.getCollectionId()));
            txtDate.setText(String.valueOf(milkStorageDto.getDate()));
            txtDuration.setText(String.valueOf(milkStorageDto.getDuration()));
            txtTemperature.setText(String.valueOf(milkStorageDto.getTemperature()));
        }
    }
}
