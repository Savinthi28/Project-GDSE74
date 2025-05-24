package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.desktop.myfx.myfinalproject.DBConnection.DBConnection;
import lk.ijse.desktop.myfx.myfinalproject.Dto.CustomerDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.CustomerModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CustomerController implements Initializable {

    @FXML
    private TableColumn<CustomerDto, String> colAddress;

    @FXML
    private TableColumn<CustomerDto, String> colCustId;

    @FXML
    private TableColumn<CustomerDto, String> colName;

    @FXML
    private TableColumn<CustomerDto, String> colNumber;

    @FXML
    private TableView<CustomerDto> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private Label lblId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNumber;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;


    private final String namePattern = "^[A-Za-z ]+$";
    private final String numberPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";


    @FXML
    void btnClearOnAction(ActionEvent event) throws SQLException {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblId.getText();
        try {
            boolean isDelete = new CustomerModel().deleteCustomer(new CustomerDto(id));
            if (isDelete) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Customer has been deleted").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer has not been deleted").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Customer has not been delete").show();
        }
    }

    private void loadNextId() throws SQLException{
        CustomerModel customerModel = new CustomerModel();
        String nextId = customerModel.getNextId();
        lblId.setText(nextId);
    }

    @FXML
    public void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException, SQLException {
        CustomerDto customerDto = new CustomerDto(lblId.getText(), txtName.getText(), txtAddress.getText(), txtNumber.getText());

        String name = txtName.getText();
        String number = txtNumber.getText();

        boolean isValidName = name.matches(namePattern);
        boolean isValidNumber = number.matches(numberPattern);

        if (isValidName && isValidNumber) {
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
    }
    private void clearFields() throws SQLException {
        loadTable();
        lblId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtNumber.setText("");

        loadNextId();
        Platform.runLater(()-> {
            lblId.setText(lblId.getText());
                });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadNextId();
            clearFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        CustomerDto customerDto = new CustomerDto(lblId.getText(), txtName.getText(), txtAddress.getText(), txtNumber.getText());

        String name = txtName.getText();
        String number = txtNumber.getText();

        boolean isValidName = name.matches(namePattern);
        boolean isValidNumber = number.matches(numberPattern);
        if (isValidName && isValidNumber) {
            try {
                boolean isSave = CustomerModel.updateCustomer(customerDto);
                if (isSave) {
                    clearFields();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Customer Updated").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer Not Updated").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Customer Not Update").show();
            }
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        CustomerDto customerDto = (CustomerDto) tblCustomer.getSelectionModel().getSelectedItem();
        if (customerDto != null) {
            lblId.setText(String.valueOf(customerDto.getCustomerId()));
            txtName.setText(customerDto.getCustomerName());
            txtAddress.setText(customerDto.getAddress());
            txtNumber.setText(customerDto.getCustomerNumber());
        }
    }

    public void txtNameChange(KeyEvent keyEvent) {
        String name = txtName.getText();
        boolean isValidName = name.matches(namePattern);
        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red");
        }else {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: blue");
        }
    }

    public void txtNumberChange(KeyEvent keyEvent) {
        String number = txtNumber.getText();
        boolean isValidNumber = number.matches(numberPattern);
        if (!isValidNumber) {
            txtNumber.setStyle(txtNumber.getStyle() + ";-fx-border-color: red");
        }else {
            txtNumber.setStyle(txtNumber.getStyle() + ";-fx-border-color: blue");
        }
    }

    public void CustomerReportOnAction(ActionEvent actionEvent) {
        CustomerDto customerDto = tblCustomer.getSelectionModel().getSelectedItem();

        if (customerDto == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a customer first!").show();
            return;
        }

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/CustomerOrderDetailsReport.jrxml"));
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = LocalDate.now().format(formatter);

            parameters.put("P_Date", formattedDate);
            parameters.put("P_Customer_ID", customerDto.getCustomerId());


            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Error in generating report: " + e.getMessage()).show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }
}

