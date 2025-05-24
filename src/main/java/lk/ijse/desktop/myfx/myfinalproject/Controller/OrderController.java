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
import lk.ijse.desktop.myfx.myfinalproject.Dto.PotsPurchaseDto; // Unused, consider removing
import lk.ijse.desktop.myfx.myfinalproject.Dto.TM.CartTM;
import lk.ijse.desktop.myfx.myfinalproject.Model.CurdProductionModel;
import lk.ijse.desktop.myfx.myfinalproject.Model.CustomerModel;
import lk.ijse.desktop.myfx.myfinalproject.Model.OrderModel;
import lk.ijse.desktop.myfx.myfinalproject.Model.PotsPurchaseModel; // Unused, consider removing

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
    private TableColumn<CartTM, String> colItemId; // Changed to String, as productionId is String

    @FXML
    private TableColumn<CartTM, String> colItemName; // Changed to String

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
    private Label lblCustomerName1; // Appears to be a duplicate or placeholder

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private Label lblID; // This holds the Order ID

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
        colItemName.setCellValueFactory(new PropertyValueFactory<>("potsSize")); // Assuming potsSize is the item name
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
        table.setItems(cartData);

        try {
            lbl_Order_Date.setText(LocalDate.now().toString());
            loadCustomerId();
            loadNextId();
            loadProductionIds(); // consolidated loadProductionIds and loadItemIds as they do the same
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize order controller: " + e.getMessage(), e);
        }
    }

    // Removed loadItemIds() as loadProductionIds() does the same thing.
    private void loadProductionIds() throws SQLException {
        ArrayList<String> productionIdList = CurdProductionModel.getAllProductionIds();
        ObservableList<String> ProductionIds = FXCollections.observableArrayList(productionIdList);
        // ProductionIds.addAll(productionIdList); // This line adds duplicates, remove it
        comProductionId.setItems(ProductionIds);
    }

    private void loadCustomerId() throws SQLException {
        ArrayList<String> customerId = OrderModel.getAllCustomerId();
        ObservableList<String> observableList = FXCollections.observableArrayList(customerId);
        // observableList.addAll(customerId); // This line adds duplicates, remove it
        comCustomerID.setItems(observableList);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void loadNextId () throws SQLException {
        OrderModel orderModel = new OrderModel();
        String id = orderModel.getNextId();
        lblID.setText(id);
    }
    public void comCustomerOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedCustomerId = comCustomerID.getSelectionModel().getSelectedItem(); // No need for casting
        if (selectedCustomerId != null && !selectedCustomerId.isEmpty()) {
            System.out.println(selectedCustomerId);
            String name = CustomerModel.findNameById(selectedCustomerId);
            System.out.println(name);
            lblCustomerName.setText(name);
        } else {
            lblCustomerName.setText(""); // Clear name if no customer selected
        }
    }

    public void comPotsSizeOnAction(ActionEvent actionEvent) {
        // This method seems to be misnamed or unused.
        // It's currently redundant if you are using comProductionIdOnAction for item selection.
        String selectedCustomerId = comCustomerID.getSelectionModel().getSelectedItem();
        System.out.println(selectedCustomerId);
    }

    private void clearFields(){
        comCustomerID.getSelectionModel().clearSelection();
        comProductionId.getSelectionModel().clearSelection();
        lblCustomerName.setText("");
        lblItemName.setText("");
        lblItemQty.setText("");
        txtUnitPrice.setText("");
        txtQuantity.setText("");
        cartData.clear(); // Clear the cart table as well
        try {
            loadNextId(); // Generate a new order ID
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error generating next order ID: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        try {
            String selectedItemId = comProductionId.getValue();
            String cartQtyString = txtQuantity.getText();

            if (selectedItemId == null || selectedItemId.isEmpty()){
                new Alert(Alert.AlertType.WARNING, "Please select a production item").show();
                return;
            }
            if (!cartQtyString.matches("^[0-9]+$") || cartQtyString.isEmpty()){
                new Alert(Alert.AlertType.WARNING, "Please enter a valid quantity (numbers only)").show();
                return;
            }
            int cartQty = Integer.parseInt(cartQtyString);

            if (cartQty <= 0){
                new Alert(Alert.AlertType.WARNING, "Quantity must be positive").show();
                return;
            }

            int currentAvailableQty;
            try {
                currentAvailableQty = Integer.parseInt(lblItemQty.getText());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Available quantity is not a valid number. Please re-select item.").show();
                return;
            }

            String itemName = lblItemName.getText();
            double unitPrice;
            try {
                unitPrice = Double.parseDouble(txtUnitPrice.getText());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Unit price is not a valid number. Please re-select item.").show();
                return;
            }

            double total = unitPrice * cartQty;

            for(CartTM existingItem : cartData){
                if(existingItem.getProductionId().equals(selectedItemId)){
                    int newQty = existingItem.getQty() + cartQty;
                    // Calculate original stock for validation purposes
                    int originalStock = currentAvailableQty + existingItem.getQty();

                    if (newQty > originalStock){
                        new Alert(Alert.AlertType.WARNING, "Cannot add more than available stock. Available: " +
                                (originalStock - existingItem.getQty())).show();
                        return;
                    }
                    existingItem.setQty(newQty);
                    existingItem.setTotalPrice(newQty * unitPrice);
                    lblItemQty.setText(String.valueOf(originalStock - newQty)); // Update displayed available quantity
                    txtQuantity.clear();
                    table.refresh();
                    return;
                }
            }
            if (cartQty > currentAvailableQty){
                new Alert(Alert.AlertType.WARNING, "Cannot add more than available stock. Available: " + currentAvailableQty).show();
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
                // When "Remove" is clicked, return the quantity to the available stock
                int currentStockDisplay = Integer.parseInt(lblItemQty.getText());
                lblItemQty.setText(String.valueOf(currentStockDisplay + newItem.getQty()));
                cartData.remove(newItem); // Remove item from cart
                table.refresh(); // Refresh the table
            });

            lblItemQty.setText(String.valueOf(currentAvailableQty - cartQty));
            cartData.add(newItem);
            txtQuantity.clear();
            table.refresh();

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Something went wrong adding to cart: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void btnPlaceOrder0nAction(ActionEvent actionEvent) {
        if (table.getItems().isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Please add items to the cart before placing an order.").show();
            return;
        }
        if (comCustomerID.getValue() == null || comCustomerID.getValue().isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Please select a customer ID.").show();
            return;
        }

        String selectedCustomerId = comCustomerID.getValue();
        String orderId = lblID.getText();
        Date date = Date.valueOf(lbl_Order_Date.getText());

        ArrayList<OrderDetailsDto> cartList = new ArrayList<>();
        int orderTotalQuantity = 0; // Renamed for clarity, represents total quantity of items in order

        for (CartTM cartTM : cartData){
            orderTotalQuantity += cartTM.getQty(); // Summing up quantities
            // Ensure productionId is used here, not productId as it's the DTO field name
            OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                    orderId, // This is correctly passed here
                    cartTM.getProductionId(), // Use getProductionId() from CartTM
                    cartTM.getQty(),
                    cartTM.getUnitPrice(),
                    cartTM.getTotalPrice() // totalPrice might not be stored in Order_Details table directly
            );
            cartList.add(orderDetailsDto);
        }

        OrderDto orderDto = new OrderDto(
                orderId,
                selectedCustomerId,
                date,
                orderTotalQuantity, // Use the summed quantity
                cartList
        );
        try {
            boolean isPlaced = OrderModel.placeOrder(orderDto);
            if(isPlaced){
                new Alert(Alert.AlertType.INFORMATION, "Order Placed Successfully!").show();
                clearFields(); // Clears all fields and table, generates new ID
            }else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed. Please check details.").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while placing the order: " + e.getMessage()).show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        // You can implement logic here if you want to select an item from the table
        // and populate the fields for editing, etc.
    }

    public void comProductionIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String selectedItemId = comProductionId.getValue();
        if (selectedItemId != null && !selectedItemId.isEmpty()){
            CurdProductionDto product = CurdProductionModel.findById(selectedItemId);
            if (product != null){
                lblItemName.setText(String.valueOf(product.getPotsSize())); // Assuming PotsSize is the descriptive name
                lblItemQty.setText(String.valueOf(product.getQuantity()));
                // If unit price is part of CurdProductionDto, set it here
                // Otherwise, you need a way to get the unit price for the product.
                // For now, txtUnitPrice is not set, which could be an issue if it's expected for calculations.
                // Assuming unit price is manually entered or fetched from another model.
                // For demonstration, let's assume `product.getUnitPrice()` exists in CurdProductionDto or related
                // If it's not directly in CurdProductionDto, you'll need another lookup.
                // For now, we'll assume it's `txtUnitPrice.getText()` is being manually set or
                // retrieved from elsewhere. If it should be fetched, add logic here.
                // Example: txtUnitPrice.setText(String.valueOf(product.getSomeUnitPrice()));
            } else {
                lblItemName.setText("");
                lblItemQty.setText("");
                txtUnitPrice.setText("");
                new Alert(Alert.AlertType.WARNING, "Production item details not found.").show();
            }
        } else {
            lblItemName.setText("");
            lblItemQty.setText("");
            txtUnitPrice.setText("");
        }
    }
}