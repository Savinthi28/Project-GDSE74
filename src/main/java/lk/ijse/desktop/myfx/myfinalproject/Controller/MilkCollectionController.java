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
import lk.ijse.desktop.myfx.myfinalproject.Dto.MilkCollectionDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.MilkCollectionModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MilkCollectionController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }

    @FXML
    private TableColumn<MilkCollectionDto, String> colBuffaloId;

    @FXML
    private TableColumn<MilkCollectionDto, String> colDate;

    @FXML
    private TableColumn<MilkCollectionDto, Integer> colId;

    @FXML
    private TableColumn<MilkCollectionDto, Double> colQuantity;

    @FXML
    private TableView<MilkCollectionDto> tblMilkCollection;

    @FXML
    private TextField txtBuffaloId;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtQuantity;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        try {
            boolean isDelete = new MilkCollectionModel().deleteMikCollection(new MilkCollectionDto(id));
            if (isDelete) {
                clearFields();
                loadTable(); 
                new Alert(Alert.AlertType.INFORMATION,"MikCollection deleted successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"MikCollection deleted Faield").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"MikCollection deleted Faield").show();
        }
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        double quantity = Double.parseDouble(txtQuantity.getText());
        MilkCollectionDto milkCollectionDto = new MilkCollectionDto(id,txtDate.getText(),quantity,txtBuffaloId.getText());

        try {
            MilkCollectionModel milkCollectionModel = new MilkCollectionModel();
            boolean isSave = milkCollectionModel.saveMilkCollection(milkCollectionDto);
            if (isSave) {
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Milk Collection has been saved successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Milk Collection has not been saved").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Milk Collection has not been saved");
        }
    }
    private void clearFields(){
        loadTable();
        txtId.setText("");
        txtDate.setText("");
        txtQuantity.setText("");
        txtBuffaloId.setText("");
    }
    private void loadTable(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colBuffaloId.setCellValueFactory(new PropertyValueFactory<>("buffaloId"));

        try {
            MilkCollectionModel milkCollectionModel = new MilkCollectionModel();
            ArrayList<MilkCollectionDto> milkCollectionDtos = milkCollectionModel.viewAllMilkCollection();
            if (milkCollectionDtos != null) {
                ObservableList<MilkCollectionDto> observableList = FXCollections.observableArrayList(milkCollectionDtos);
                tblMilkCollection.setItems(observableList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        double quantity = Double.parseDouble(txtQuantity.getText());
        MilkCollectionDto milkCollectionDto = new MilkCollectionDto(id,txtDate.getText(),quantity,txtBuffaloId.getText());
        try {
            boolean isSave = MilkCollectionModel.updateMilkCollection(milkCollectionDto);
            if (isSave) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION,"Milk Collection has been updated successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Milk Collection has not been updated").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Milk Collection has not been update");
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        MilkCollectionDto milkCollectionDto = (MilkCollectionDto) tblMilkCollection.getSelectionModel().getSelectedItem();
        if (milkCollectionDto != null) {
            txtId.setText(String.valueOf(milkCollectionDto.getId()));
            txtDate.setText(milkCollectionDto.getDate());
            txtQuantity.setText(String.valueOf(milkCollectionDto.getQuantity()));
            txtBuffaloId.setText(milkCollectionDto.getBuffaloId());
        }
    }
}
