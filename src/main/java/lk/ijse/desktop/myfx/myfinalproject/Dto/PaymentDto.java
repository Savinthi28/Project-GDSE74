package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PaymentDto {
    private Integer paymentId;
    private Integer orderId;
    private Integer customerId;
    private String date;
    private String paymentMethod;
    private double amount;
}
