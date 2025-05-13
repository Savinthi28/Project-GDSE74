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
import lk.ijse.desktop.myfx.myfinalproject.Dto.PaymentDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.PaymentModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private TableColumn<PaymentDto, Double> colAmount;

    @FXML
    private TableColumn<PaymentDto, Integer> colCustomerId;

    @FXML
    private TableColumn<PaymentDto, String> colDate;

    @FXML
    private TableColumn<PaymentDto, String> colMethod;

    @FXML
    private TableColumn<PaymentDto, Integer> colOrderId;

    @FXML
    private TableColumn<PaymentDto, Integer> colPaymentId;

    @FXML
    private TableView<PaymentDto> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtPaymentId;

    @FXML
    private TextField txtPaymentMethod;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtPaymentId.getText());
        try {
            boolean isDelete = new PaymentModel().deletePayment(new PaymentDto(id));
            if (isDelete) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Payment Deleted").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Payment Not Deleted").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Payment Not Delete").show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        int paymentId = Integer.parseInt(txtPaymentId.getText());
        int orderId=Integer.parseInt(txtOrderId.getText());
        int customerId=Integer.parseInt(txtCustomerId.getText());
        double amount=Double.parseDouble(txtAmount.getText());
        PaymentDto paymentDto = new PaymentDto(paymentId,orderId,customerId,txtDate.getText(),txtPaymentMethod.getText(),amount);

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
    private void clearFields(){
        loadTable();
        txtPaymentId.setText("");
        txtOrderId.setText("");
        txtCustomerId.setText("");
        txtDate.setText("");
        txtPaymentMethod.setText("");
        txtAmount.setText("");
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
        loadTable();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        clearFields();
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        PaymentDto paymentDto = (PaymentDto) tblPayment.getSelectionModel().getSelectedItem();
        if (paymentDto != null) {
            txtPaymentId.setText(String.valueOf(paymentDto.getPaymentId()));
            txtOrderId.setText(String.valueOf(paymentDto.getOrderId()));
            txtCustomerId.setText(String.valueOf(paymentDto.getCustomerId()));
            txtDate.setText(String.valueOf(paymentDto.getDate()));
            txtPaymentMethod.setText(String.valueOf(paymentDto.getPaymentMethod()));
            txtAmount.setText(String.valueOf(paymentDto.getAmount()));
        }
    }
}
