package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDto {
    private String orderId;
    private String customerId;
    private String date;

    public OrderDto(String id) {
        this.orderId = id;
    }
}
