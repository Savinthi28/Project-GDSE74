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
import lk.ijse.desktop.myfx.myfinalproject.Dto.SupplierDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    @FXML
    private TableColumn<SupplierDto, String> colAddress;

    @FXML
    private TableColumn<SupplierDto, Integer> colId;

    @FXML
    private TableColumn<SupplierDto, String> colName;

    @FXML
    private TableColumn<SupplierDto, String> colNumber;

    @FXML
    private TableView<SupplierDto> tblSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNumber;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        try {
            boolean isDelete = new SupplierModel().deleteSupplier(new SupplierDto(id));
            if (isDelete) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Deletion Failed").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Delete Failed").show();
        }
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        int id = Integer.parseInt(txtId.getText());
        SupplierDto supplierDto = new SupplierDto(id, txtName.getText(), txtNumber.getText(), txtAddress.getText());

        try {
            SupplierModel supplierModel = new SupplierModel();
            boolean isSave = supplierModel.saveSupplier(supplierDto);
            if (isSave) {
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Supplier has been saved successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to save supplier").show();
            }
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save supplier").show();
        }
    }

    private void clearFields() {
        loadTable();
        txtAddress.setText("");
        txtId.setText("");
        txtName.setText("");
        txtNumber.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }

    private void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        try {
            SupplierModel supplierModel = new SupplierModel();
            ArrayList<SupplierDto> supplierDtos = supplierModel.viewAllSupplier();
            if (supplierDtos != null) {
                ObservableList<SupplierDto> supplierList = FXCollections.observableArrayList(supplierDtos);
                tblSupplier.setItems(supplierList);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    public void tableOnClick(MouseEvent mouseEvent) {
        SupplierDto supplierDto = (SupplierDto) tblSupplier.getSelectionModel().getSelectedItem();
        if (supplierDto != null) {
            txtId.setText(String.valueOf(supplierDto.getSupplierId()));
            txtName.setText(supplierDto.getSupplierName());
            txtNumber.setText(supplierDto.getContactNumber());
            txtAddress.setText(supplierDto.getAddress());
        }
    }
}
