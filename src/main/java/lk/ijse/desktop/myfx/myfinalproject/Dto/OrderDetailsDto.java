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
}
