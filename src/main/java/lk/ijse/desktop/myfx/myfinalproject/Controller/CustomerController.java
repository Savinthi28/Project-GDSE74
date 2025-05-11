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
import lk.ijse.desktop.myfx.myfinalproject.Dto.CustomerDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.CustomerModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private TableColumn<CustomerDto, String> colAddress;

    @FXML
    private TableColumn<CustomerDto, Integer> colCustId;

    @FXML
    private TableColumn<CustomerDto, String> colName;

    @FXML
    private TableColumn<CustomerDto, String> colNumber;

    @FXML
    private TableView<CustomerDto> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCustId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNumber;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        int id = Integer.parseInt(txtCustId.getText());
        CustomerDto customerDto = new CustomerDto(id, txtName.getText(), txtAddress.getText(), txtNumber.getText());

        try {
            CustomerModel customerModel = new CustomerModel();
            boolean isSave = customerModel.saveCustomer(customerDto);
            if (isSave) {
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Saved").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Customer Not Saved").show();
        }
    }
    private void clearFields(){
        loadTable();
        txtCustId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtNumber.setText("");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }
    private void loadTable(){
        colCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("customerNumber"));

        try {
            CustomerModel customerModel = new CustomerModel();
            ArrayList<CustomerDto> customerDtos = customerModel.viewAllCustomer();
            if (customerDtos != null){
                ObservableList<CustomerDto> observableList = FXCollections.observableArrayList(customerDtos);
                tblCustomer.setItems(observableList);
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
