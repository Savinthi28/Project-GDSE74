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
import lk.ijse.desktop.myfx.myfinalproject.Model.OrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadNextId();
            loadCustomerId();
            loadPotsSize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadTable();
    }

    private void loadPotsSize() throws SQLException {
        ArrayList<Integer> potsSize = OrderModel.getAllPotsSize();
        ObservableList<Integer> observableList = FXCollections.observableArrayList(potsSize);
        observableList.addAll(potsSize);
        comPotsSize.setItems(observableList);
    }

    private void loadCustomerId() throws SQLException {
        ArrayList<Integer> customerId = OrderModel.getAllCustomerId();
        ObservableList<Integer> observableList = FXCollections.observableArrayList(customerId);
        observableList.addAll(customerId);
        comCustomerId.setItems(observableList);
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
    private ComboBox<Integer> comCustomerId;

    @FXML
    private ComboBox<Integer> comPotsSize;

    @FXML
    private TextField txtDate;

    @FXML
    private Label lblId;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtTime;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(lblId.getText());
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
    }

    private void loadNextId () throws SQLException {
        OrderModel orderModel = new OrderModel();
        String id = orderModel.getNextId();
        lblId.setText(id);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        int orderId = Integer.parseInt(lblId.getText());
        int customerId=Integer.parseInt(String.valueOf(comCustomerId.getValue()));
        Time time = Time.valueOf(txtTime.getText());
        int potsSize = Integer.parseInt(String.valueOf(comPotsSize.getValue()));
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
        lblId.setText("");
        comCustomerId.setValue(Integer.valueOf(""));
        txtDate.setText("");
        txtTime.setText("");
        comPotsSize.setValue(Integer.valueOf(""));
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
    public void btnUpdateOnAction(ActionEvent event) {
        int orderId = Integer.parseInt(lblId.getText());
        int customerId = Integer.parseInt(String.valueOf(comCustomerId.getValue()));
        Time time = Time.valueOf(txtTime.getText());
        int potsSize = Integer.parseInt(String.valueOf(comPotsSize.getValue()));
        int quantity = Integer.parseInt(txtQuantity.getText());
        OrderDto orderDto = new OrderDto(orderId,customerId,txtDate.getText(),time,potsSize,quantity);
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
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        OrderDto orderDto = (OrderDto) tblOrder.getSelectionModel().getSelectedItem();
        if(orderDto != null){
            lblId.setText(String.valueOf(orderDto.getOrderId()));
            comCustomerId.setValue(Integer.valueOf(String.valueOf(orderDto.getCustomerId())));
            txtDate.setText(String.valueOf(orderDto.getDate()));
            txtTime.setText(String.valueOf(orderDto.getTime()));
            comPotsSize.setValue(Integer.valueOf(String.valueOf(orderDto.getPotsSize())));
            txtQuantity.setText(String.valueOf(orderDto.getQuantity()));
        }
    }

    public void comCustomerIdOnAction(ActionEvent actionEvent) {
        Integer selectedCustomerId = (Integer) comCustomerId.getSelectionModel().getSelectedItem();
        System.out.println(selectedCustomerId);
    }

    public void comPotsSizeOnAction(ActionEvent actionEvent) {
        Integer selectedPotsSize = (Integer) comPotsSize.getSelectionModel().getSelectedItem();
        System.out.println(selectedPotsSize);
    }
}
