package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDetailsDto {
    private String orderId;
    private String productionId;
    private int quantity;
    private double unitPrice;

    public OrderDetailsDto(String orderId, String productId, int qty, double unitPrice, double totalPrice) {
    }

}
