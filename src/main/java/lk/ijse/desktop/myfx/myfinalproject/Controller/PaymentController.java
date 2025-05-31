package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.desktop.myfx.myfinalproject.Dto.PaymentDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.PaymentModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private TableColumn<PaymentDto, Double> colAmount;

    @FXML
    private TableColumn<PaymentDto, String> colCustomerId;

    @FXML
    private TableColumn<PaymentDto, String> colDate;

    @FXML
    private TableColumn<PaymentDto, String> colMethod;

    @FXML
    private TableColumn<PaymentDto, String> colOrderId;

    @FXML
    private TableColumn<PaymentDto, String> colPaymentId;

    @FXML
    private TableView<PaymentDto> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private ComboBox<String> comCustomerId;

    @FXML
    private ComboBox<String> comOrderId;

    @FXML
    private ComboBox<String> comPaymentMethod;

    @FXML
    private TextField txtDate;

    @FXML
    private Label lblId;

    @FXML
    void btnClearOnAction(ActionEvent event) throws SQLException {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Payment");
        alert.setContentText("Are you sure you want to delete this payment?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isDelete = new PaymentModel().deletePayment(new PaymentDto(id));
                if (isDelete) {
                    clearFields();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Payment Deleted").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Payment Not Deleted").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Payment Not Delete").show();
            }
        }
    }

    private void loadNextId() throws SQLException {
        PaymentModel paymentModel = new PaymentModel();
        String id = paymentModel.getNextId();
        lblId.setText(id);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        double amount=Double.parseDouble(txtAmount.getText());
        PaymentDto paymentDto = new PaymentDto(lblId.getText(),comOrderId.getValue(),comCustomerId.getValue(),txtDate.getText(),comPaymentMethod.getValue(),amount);

        try {
            PaymentModel paymentModel = new PaymentModel();
            boolean isSaved = paymentModel.savePayment(paymentDto);
            if (isSaved) {
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Payment Saved").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Payment Not Saved").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Payment Not Saved").show();
        }
    }
    private void clearFields() throws SQLException {
        loadTable();
        lblId.setText("");
        comOrderId.setValue("");
        comCustomerId.setValue("");
        txtDate.setText("");
        comPaymentMethod.setValue("");
        txtAmount.setText("");

        loadNextId();
        Platform.runLater(()-> {
            lblId.setText(lblId.getText());
        });
    }
    private void loadTable(){
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        try {
            PaymentModel paymentModel = new PaymentModel();
            ArrayList<PaymentDto> paymentDtos = paymentModel.viewAllPayment();
            if (paymentDtos != null) {
                ObservableList<PaymentDto> payments = FXCollections.observableArrayList(paymentDtos);
                tblPayment.setItems(payments);
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
            loadOrderId();
            loadCustomerId();
            loadPaymentMethod();
            clearFields();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }

    private void loadCustomerId() throws SQLException {
        ArrayList<String> customerIds = PaymentModel.getAllCustomerId();
        ObservableList<String> customers = FXCollections.observableArrayList(customerIds);
        customers.addAll(customerIds);
        comCustomerId.setItems(customers);
    }

    private void loadOrderId() throws SQLException {
        ArrayList<String> orderId = PaymentModel.getAllOrderId();
        ObservableList<String> orders = FXCollections.observableArrayList(orderId);
        orders.addAll(orderId);
        comOrderId.setItems(orders);
    }

    private void loadPaymentMethod() throws SQLException {
        ArrayList<String> paymentMethods = PaymentModel.getAllPaymentMethod();
        ObservableList<String> payments = FXCollections.observableArrayList(paymentMethods);
        payments.addAll(paymentMethods);
        comPaymentMethod.setItems(payments);
    }

    @FXML
    public void btnUpdateOnAction(ActionEvent event) {
        double amount = Double.parseDouble(txtAmount.getText());
        PaymentDto paymentDto = new PaymentDto(lblId.getText(), comOrderId.getValue(), comCustomerId.getValue(), txtDate.getText(), comPaymentMethod.getValue(), amount);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Update Payment");
        alert.setContentText("Are you sure you want to update this payment?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isSave = PaymentModel.updatePayment(paymentDto);
                if (isSave) {
                    clearFields();
                    loadTable();
                    new Alert(Alert.AlertType.INFORMATION, "Payment Updated Successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Payment Not Updated").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Payment Not Updated").show();
            }
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        PaymentDto paymentDto = (PaymentDto) tblPayment.getSelectionModel().getSelectedItem();
        if (paymentDto != null) {
            lblId.setText(String.valueOf(paymentDto.getPaymentId()));
            comOrderId.setValue(paymentDto.getOrderId());
            comCustomerId.setValue(paymentDto.getCustomerId());
            txtDate.setText(String.valueOf(paymentDto.getDate()));
            comPaymentMethod.setValue(String.valueOf(paymentDto.getPaymentMethod()));
            txtAmount.setText(String.valueOf(paymentDto.getAmount()));
        }
    }

    public void comOrderIdOnAction(ActionEvent actionEvent) {
        String selectedOrderId = (String) comOrderId.getSelectionModel().getSelectedItem();
        System.out.println(selectedOrderId);
    }

    public void comCustomerIdOnAction(ActionEvent actionEvent) {
        String selectedCustomerId = (String) comCustomerId.getSelectionModel().getSelectedItem();
        System.out.println(selectedCustomerId);
    }

    public void comPaymentMethodOnAction(ActionEvent actionEvent) {
        String selectedPaymentMethod = (String) comPaymentMethod.getSelectionModel().getSelectedItem();
        System.out.println(selectedPaymentMethod);
    }
}
