package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDto {
    private String orderId;
    private String customerId;
    private String date;
    private int orderTotal;
    private ArrayList<OrderDetailsDto> cartList;

    public OrderDto(String id) {
        this.orderId = id;
    }

    public OrderDto(String orderId, String selectedCustomerId, Date date, int orderTotal, ArrayList<OrderDetailsDto> cartList) {
    }

    public OrderDto(String orderId, String customerId, String orderDate) {
    }
}
