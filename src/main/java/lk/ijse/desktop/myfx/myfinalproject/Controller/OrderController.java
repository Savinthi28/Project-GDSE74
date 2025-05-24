package lk.ijse.desktop.myfx.myfinalproject.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.desktop.myfx.myfinalproject.Dto.CurdProductionDto;
import lk.ijse.desktop.myfx.myfinalproject.Dto.OrderDetailsDto;
import lk.ijse.desktop.myfx.myfinalproject.Dto.OrderDto;
import lk.ijse.desktop.myfx.myfinalproject.Dto.PotsPurchaseDto;
import lk.ijse.desktop.myfx.myfinalproject.Dto.TM.CartTM;
import lk.ijse.desktop.myfx.myfinalproject.Model.CurdProductionModel;
import lk.ijse.desktop.myfx.myfinalproject.Model.CustomerModel;
import lk.ijse.desktop.myfx.myfinalproject.Model.OrderModel;
import lk.ijse.desktop.myfx.myfinalproject.Model.PotsPurchaseModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class OrderController implements Initializable {
    @FXML
    private TableColumn<CartTM, String> colAction;

    @FXML
    private TableColumn<CartTM, Integer> colItemId;

    @FXML
    private TableColumn<CartTM, String> colItemName;

    @FXML
    private TableColumn<CartTM, Integer> colQty;

    @FXML
    private TableColumn<CartTM, Double> colTotalPrice;

    @FXML
    private TableColumn<CartTM, Double> colUnitPrice;

    @FXML
    private ComboBox<String> comCustomerID;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblCustomerName1;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private Label lblID;

    @FXML
    private Label lblItemName;

    @FXML
    private TextField txtQuantity;

    @FXML
    private Label lbl_Order_Date;

    @FXML
    private Label lblItemQty;

    @FXML
    private ComboBox<String> comProductionId;

    @FXML
    private TableView<CartTM> table;

    private final ObservableList<CartTM> cartData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("productionId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("potsSize"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
        table.setItems(cartData);

        try {
            lbl_Order_Date.setText(LocalDate.now().toString());
            loadCustomerId();
            loadNextId();
            loadCustomerId();
            loadProductionIds();
            loadItemIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // loadTable();
    }

    private void loadItemIds() throws SQLException{
        comProductionId.setItems(FXCollections.observableArrayList(CurdProductionModel.getAllProductionIds()));
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
        try {
            String selectedItemId = comProductionId.getValue();
            String cartQtyString = txtQuantity.getText();

            if (selectedItemId == null){
                new Alert(Alert.AlertType.WARNING, "Please select production item").show();
                return;
            }
            if (!cartQtyString.matches("^[0-9]+$") || cartQtyString.isEmpty()){
                new Alert(Alert.AlertType.WARNING, "Please enter valid quantity").show();
                return;
            }
            int cartQty = Integer.parseInt(cartQtyString);
            int currentAvailableQty = Integer.parseInt(lblItemQty.getText());
            String itemName = lblItemName.getText();
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            double total = unitPrice * cartQty;

            if (cartQty <= 0){
                new Alert(Alert.AlertType.WARNING, "Quantity must be positive").show();
                return;
            }
            for(CartTM existingItem : cartData){
                if(existingItem.getProductionId().equals(selectedItemId)){
                    int newQty = existingItem.getQty() + cartQty;
                    int originalStock = currentAvailableQty + existingItem.getQty();

                    if (newQty > originalStock){
                        new Alert(Alert.AlertType.WARNING, "Cannot add more than available stock."+
                                (originalStock - existingItem.getQty())).show();
                        return;
                    }
                    existingItem.setQty(newQty);
                    existingItem.setTotalPrice(newQty * unitPrice);
                    lblItemQty.setText(String.valueOf(originalStock - newQty));
                    txtQuantity.clear();
                    table.refresh();
                    return;
                }
            }
            if (cartQty > currentAvailableQty){
                new Alert(Alert.AlertType.WARNING, "Cannot add more than available stock."+ currentAvailableQty).show();
                return;
            }
            Button removeBtn = new Button("Remove");
            CartTM newItem = new CartTM(
                    selectedItemId,
                    itemName,
                    cartQty,
                    unitPrice,
                    total,
                    removeBtn
            );

            removeBtn.setOnAction((ActionEvent event) -> {
                int currentStock = Integer.parseInt(lblItemQty.getText());
                lblItemQty.setText(String.valueOf(currentStock + newItem.getQty()));
                table.refresh();
            });
            lblItemQty.setText(String.valueOf(currentAvailableQty - cartQty));
            cartData.add(newItem);
            txtQuantity.clear();
            table.refresh();

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Something went wrong" + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void btnPlaceOrder0nAction(ActionEvent actionEvent) {
        if (table.getItems().isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Please select production item").show();
            return;
        }
        if (comCustomerID.getValue() == null){
            new Alert(Alert.AlertType.WARNING, "Please select customer ID").show();
            return;
        }
        String selectedCustomerId = comCustomerID.getValue();
        String orderId = lblID.getText();
        Date date = Date.valueOf(lbl_Order_Date.getText());
        String productId = comProductionId.getValue();

        ArrayList<OrderDetailsDto> cartList = new ArrayList<>();
        int orderTotal = 0;

        for (CartTM cartTM : cartData){
            orderTotal += cartTM.getQty();
            OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                    orderId,
                    productId,
                    cartTM.getQty(),
                    cartTM.getUnitPrice(),
                    cartTM.getTotalPrice()
            );
            cartList.add(orderDetailsDto);
        }
        OrderDto orderDto = new OrderDto(
                orderId,
                selectedCustomerId,
                date,
                orderTotal,
                cartList
        );
        try {
            boolean isPlaced = OrderModel.placeOrder(orderDto);
            if(isPlaced){
                clearFields();
                new Alert(Alert.AlertType.INFORMATION, "Order Placed").show();
                table.getItems().clear();
            }else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
    }

    public void tableOnClick(MouseEvent mouseEvent) {
    }

    public void comProductionIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       /* String selectedProductionId = (String) comProductionId.getSelectionModel().getSelectedItem();
        System.out.println(selectedProductionId);
        int potSize = PotsPurchaseModel.findpotById(selectedProductionId);
        System.out.println(potSize);
        lblItemQty.setText(String.valueOf(potSize));

        */
        String selectedItemId = comProductionId.getValue();
        if (selectedItemId != null){
            CurdProductionDto product = CurdProductionModel.findById(selectedItemId);
            if (product != null){
                lblItemName.setText(String.valueOf(product.getPotsSize()));
                lblItemQty.setText(String.valueOf(product.getQuantity()));
                txtUnitPrice.getText();
            }
        }
    }
}
