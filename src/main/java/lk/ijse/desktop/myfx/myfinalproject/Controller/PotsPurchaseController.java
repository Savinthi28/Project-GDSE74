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
import lk.ijse.desktop.myfx.myfinalproject.Dto.PotsPurchaseDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.PotsPurchaseModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PotsPurchaseController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }

    @FXML
    private TableColumn<PotsPurchaseDto, String> colDate;

    @FXML
    private TableColumn<PotsPurchaseDto, Integer> colId;

    @FXML
    private TableColumn<PotsPurchaseDto, Integer> colPotsSize;

    @FXML
    private TableColumn<PotsPurchaseDto, Double> colPrice;

    @FXML
    private TableColumn<PotsPurchaseDto, Integer> colQuantity;

    @FXML
    private TableView<PotsPurchaseDto> tblPotsPurchase;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPotsSize;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        try {
            boolean isDelete = new PotsPurchaseModel().deletePotsPurchase(new PotsPurchaseDto(id));
            if (isDelete) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Pots Purchase Deleted Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Pots Purchase Deletion Failed").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Pots Purchase Deletion Failed").show();
        }
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        int potsSize = Integer.parseInt(txtPotsSize.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        PotsPurchaseDto potsPurchaseDto = new PotsPurchaseDto(id,potsSize,txtDate.getText(),quantity,unitPrice);

        try {
            PotsPurchaseModel potsPurchaseModel = new PotsPurchaseModel();
            boolean isSave = potsPurchaseModel.savePotsPurchase(potsPurchaseDto);
            if (isSave) {
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Pots Purchase Successfully Saved").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Pots Purchase Failed").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Pots Purchase Failed").show();
        }
    }
    private void clearFields(){
        loadTable();
        txtId.setText("");
        txtPotsSize.setText("");
        txtDate.setText("");
        txtQuantity.setText("");
        txtUnitPrice.setText("");
    }
    private void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("purchaseId"));
        colPotsSize.setCellValueFactory(new PropertyValueFactory<>("potsSize"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            PotsPurchaseModel potsPurchaseModel = new PotsPurchaseModel();
            ArrayList<PotsPurchaseDto> potsPurchaseDtos = potsPurchaseModel.viewAllPotsPurchase();
            if (potsPurchaseDtos != null) {
                ObservableList<PotsPurchaseDto> list = FXCollections.observableArrayList(potsPurchaseDtos);
                tblPotsPurchase.setItems(list);
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
        int potsSize = Integer.parseInt(txtPotsSize.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        PotsPurchaseDto potsPurchaseDto = new PotsPurchaseDto(id,potsSize,txtDate.getText(),quantity,unitPrice);
        try {
            boolean isSave = PotsPurchaseModel.updatePotsPurchase(potsPurchaseDto);
            if (isSave) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Pots Purchase Successfully Updated").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Pots Purchase Failed").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Pots Purchase Faile").show();
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        PotsPurchaseDto potsPurchaseDto = (PotsPurchaseDto) tblPotsPurchase.getSelectionModel().getSelectedItem();
        if (potsPurchaseDto != null) {
            txtId.setText(String.valueOf(potsPurchaseDto.getPurchaseId()));
            txtPotsSize.setText(String.valueOf(potsPurchaseDto.getPotsSize()));
            txtDate.setText(potsPurchaseDto.getDate());
            txtQuantity.setText(String.valueOf(potsPurchaseDto.getQuantity()));
            txtUnitPrice.setText(String.valueOf(potsPurchaseDto.getPrice()));
        }
    }
}
