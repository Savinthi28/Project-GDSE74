package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.desktop.myfx.myfinalproject.Dto.OrderDto;
import lk.ijse.desktop.myfx.myfinalproject.Model.CurdProductionModel;
import lk.ijse.desktop.myfx.myfinalproject.Model.CustomerModel;
import lk.ijse.desktop.myfx.myfinalproject.Model.OrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class OrderController implements Initializable {
    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotalPrice;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private ComboBox<String> comCustomerID;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblCustomerName1;

    @FXML
    private Label lblID;
    @FXML
    private Label lblPotSize;

    @FXML
    private Label lbl_Order_Date;

    @FXML
    private ComboBox<String> comProductionId;

    @FXML
    private TableView<?> tblOrder;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lbl_Order_Date.setText(LocalDate.now().toString());
            loadCustomerId();
            loadNextId();
            loadCustomerId();
            loadProductionIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // loadTable();
    }

    private void loadProductionIds() throws SQLException {
        ArrayList<String> productionIdList = CurdProductionModel.getAllProductionIds();
        ObservableList<String> ProductionIds = FXCollections.observableArrayList(productionIdList);
        ProductionIds.addAll(productionIdList);
        comProductionId.setItems(ProductionIds);
    }

    private void loadCustomerId() throws SQLException {
        ArrayList<String> customerId = OrderModel.getAllCustomerId();
        ObservableList<String> observableList = FXCollections.observableArrayList(customerId);
        observableList.addAll(customerId);
        comCustomerID.setItems(observableList);
    }




    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
   /* void btnDeleteOnAction(ActionEvent event) {
        String id = lblId.getText();
        try {
            boolean isDelete = new OrderModel().deleteOrder(new OrderDto(id));
            if (isDelete) {
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "Order Deleted").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Order Not Deleted").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Order Not Delete").show();
        }
    }*/

    private void loadNextId () throws SQLException {
        OrderModel orderModel = new OrderModel();
        String id = orderModel.getNextId();
        lblID.setText(id);
    }

    @FXML
   /* void btnSaveOnAction(ActionEvent event) {

        OrderDto orderDto = new OrderDto(lblId.getText(),comCustomerId.getValue(),txtDate.getText());

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
        lblId.setText("");
        comCustomerId.setValue("");
        txtDate.setText("");
    }
    private void loadTable(){
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

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
    public void btnUpdateOnAction(ActionEvent event) {

        OrderDto orderDto = new OrderDto(lblId.getText(),comCustomerId.getValue(),txtDate.getText());
        try {
            boolean isSave = OrderModel.updateOrder(orderDto);
            if(isSave){
                clearFields();
                loadTable();
                new Alert(Alert.AlertType.INFORMATION,"Order Updated").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Order Not Updated").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Order Not Updated").show();
        }
    }*/

   /* public void tableOnClick(MouseEvent mouseEvent) {
        OrderDto orderDto = (OrderDto) tblOrder.getSelectionModel().getSelectedItem();
        if(orderDto != null){
            lblId.setText(orderDto.getOrderId());
            comCustomerId.setValue(orderDto.getCustomerId());
            txtDate.setText(String.valueOf(orderDto.getDate()));
        }
    }*/

    public void comCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedCustomerId = (String) comCustomerID.getSelectionModel().getSelectedItem();
        System.out.println(selectedCustomerId);
        String name = CustomerModel.findNameById(selectedCustomerId);
        System.out.println(name);
        lblCustomerName.setText(name);
    }

    public void comPotsSizeOnAction(ActionEvent actionEvent) {
        String selectedCustomerId = (String) comCustomerID.getSelectionModel().getSelectedItem();
        System.out.println(selectedCustomerId);
    }
    private void clearFields(){
        // loadTable();

    }



    public void btnAddToCartOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrder0nAction(ActionEvent actionEvent) {
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
    }

    public void tableOnClick(MouseEvent mouseEvent) {
    }

    public void comProductionIdOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedProductionId = (String) comProductionId.getSelectionModel().getSelectedItem();
        System.out.println(selectedProductionId);
        int potSize = CurdProductionModel.findpotById(selectedProductionId);
        System.out.println(potSize);
        lblPotSize.setText(String.valueOf(potSize));
    }
}
