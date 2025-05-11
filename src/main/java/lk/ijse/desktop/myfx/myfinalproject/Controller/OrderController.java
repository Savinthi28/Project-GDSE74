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
import lk.ijse.desktop.myfx.myfinalproject.Dto.OrderDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.OrderModel;

import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }

    @FXML
    private TableColumn<OrderDto, Integer> colCustomerId;

    @FXML
    private TableColumn<OrderDto, String> colDate;

    @FXML
    private TableColumn<OrderDto, Integer> colOrderId;

    @FXML
    private TableColumn<OrderDto, Integer> colPotsSize;

    @FXML
    private TableColumn<OrderDto, Integer> colQuantity;

    @FXML
    private TableColumn<OrderDto, Time> colTime;

    @FXML
    private TableView<OrderDto> tblOrder;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtPotsSize;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtTime;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        int orderId = Integer.parseInt(txtOrderId.getText());
        int customerId=Integer.parseInt(txtCustomerId.getText());
        Time time = Time.valueOf(txtTime.getText());
        int potsSize = Integer.parseInt(txtPotsSize.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        OrderDto orderDto = new OrderDto(orderId,customerId,txtDate.getText(),time,potsSize,quantity);

        try {
            OrderModel orderModel = new OrderModel();
            boolean isSaved = orderModel.saveOrder(orderDto);
            if(isSaved){
                clearFields();
                new Alert(Alert.AlertType.INFORMATION,"Order Saved").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Order Not Saved").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Order Not Saved").show();
        }
    }
    private void clearFields(){
        loadTable();
        txtOrderId.setText("");
        txtCustomerId.setText("");
        txtDate.setText("");
        txtTime.setText("");
        txtPotsSize.setText("");
        txtQuantity.setText("");
    }
    private void loadTable(){
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colPotsSize.setCellValueFactory(new PropertyValueFactory<>("potsSize"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        try {
            OrderModel orderModel = new OrderModel();
            ArrayList<OrderDto> orderDtos = orderModel.viewAllOrder();
            if(orderDtos != null ){
                ObservableList<OrderDto> orders = FXCollections.observableArrayList(orderDtos);
                tblOrder.setItems(orders);
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
