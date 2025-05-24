package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
//@AllArgsConstructor // Keep this for the main constructor
@NoArgsConstructor
@ToString
public class OrderDto {
    private String orderId;
    private String customerId;
    private Date date; // Changed from String to Date for consistency with java.sql.Date
    private int orderTotal;
    private ArrayList<OrderDetailsDto> cartList;

    // **FIXED CONSTRUCTORS (if you intend to use them)**
    // The previous constructors were also empty, potentially causing issues
    // if you were relying on them to initialize fields for other purposes.
    // However, for the primary order placement logic, the @AllArgsConstructor will be used.

    public OrderDto(String id) {
        this.orderId = id;
    }

    public OrderDto(String orderId, String selectedCustomerId, Date date, int orderTotal, ArrayList<OrderDetailsDto> cartList) {
        this.orderId = orderId;
        this.customerId = selectedCustomerId;
        this.date = date;
        this.orderTotal = orderTotal;
        this.cartList = cartList;
    }

    public OrderDto(String orderId, String customerId, String orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.date = Date.valueOf(orderDate); // Convert String date to Date
    }
}