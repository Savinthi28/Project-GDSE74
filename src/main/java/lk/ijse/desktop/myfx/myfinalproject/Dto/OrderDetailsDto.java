package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor // Keep this for the main constructor
@NoArgsConstructor
@ToString
public class OrderDetailsDto {
    private String orderId;
    private String productionId; // This should be 'productionId' to match the database column
    private int quantity;
    private double unitPrice;
    // Add totalPrice if you need it in the DTO, but it's not in your current table structure for Order_Details

    // **FIXED CONSTRUCTOR**
    // This constructor was missing the assignment of parameters to the fields.
    // Ensure you use the correct field names: productionId (not productId)
    public OrderDetailsDto(String orderId, String productionId, int quantity, double unitPrice, double totalPrice) {
        this.orderId = orderId;
        this.productionId = productionId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        // If totalPrice is not stored in the database for Order_Details, you don't need to pass it here
        // or ensure your table structure for Order_Details supports it.
        // Based on Order_Details values (?,?,?,?), it currently doesn't store totalPrice.
        // If you intended to use 'totalPrice' for some logic within the DTO, it should be a field.
        // For the purpose of database insertion, we'll stick to the current table structure (orderId, productionId, quantity, unitPrice).
    }
}