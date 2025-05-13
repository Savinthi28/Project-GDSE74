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
import lk.ijse.desktop.myfx.myfinalproject.Dto.PotsInventoryDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.PotsInventoryModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PotsInventoryController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }

    @FXML
    private TableColumn<PotsInventoryDto, String> colCondition;

    @FXML
    private TableColumn<PotsInventoryDto, Integer> colId;

    @FXML
    private TableColumn<PotsInventoryDto, Integer> colPotsSize;

    @FXML
    private TableColumn<PotsInventoryDto, Integer> colQuantity;

    @FXML
    private TableView<PotsInventoryDto> tblPotsInventory;

    @FXML
    private TextField txtCondition;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPotsSize;

    @FXML
    private TextField txtQuantity;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        try {
            boolean isDelete = new PotsInventoryModel().deletePotsInventory(new PotsInventoryDto(id));
            if (isDelete) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Pots deleted successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Pots not deleted successfully").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Pots not deleted successfully").show();
        }
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        int potsSize = Integer.parseInt(txtPotsSize.getText());
        PotsInventoryDto potsInventoryDto = new PotsInventoryDto(id,quantity,potsSize,txtCondition.getText());

        try {
            PotsInventoryModel potsInventoryModel = new PotsInventoryModel();
            boolean isSave = potsInventoryModel.savePotsInventory(potsInventoryDto);
            if (isSave) {
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Pots Inventory Saved").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Pots Inventory Not Saved").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Pots Inventory Not Saved").show();
        }
    }
    private void clearFields() {
        loadTable();
        txtId.setText("");
        txtQuantity.setText("");
        txtPotsSize.setText("");
        txtCondition.setText("");
    }
    private void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPotsSize.setCellValueFactory(new PropertyValueFactory<>("potsSize"));
        colCondition.setCellValueFactory(new PropertyValueFactory<>("condition"));

        try {
            PotsInventoryModel potsInventoryModel = new PotsInventoryModel();
            ArrayList<PotsInventoryDto> potsInventoryDtos = potsInventoryModel.viewAllPotsInventory();
            if (potsInventoryDtos != null) {
                ObservableList<PotsInventoryDto> observableList = FXCollections.observableArrayList(potsInventoryDtos);
                tblPotsInventory.setItems(observableList);
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
        PotsInventoryDto potsInventoryDto = (PotsInventoryDto) tblPotsInventory.getSelectionModel().getSelectedItem();
        if (potsInventoryDto != null) {
            txtId.setText(String.valueOf(potsInventoryDto.getId()));
            txtQuantity.setText(String.valueOf(potsInventoryDto.getQuantity()));
            txtPotsSize.setText(String.valueOf(potsInventoryDto.getPotsSize()));
            txtCondition.setText(String.valueOf(potsInventoryDto.getCondition()));
        }
    }
}
