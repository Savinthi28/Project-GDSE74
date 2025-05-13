package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDto {
    private int orderId;
    private int customerId;
    private String date;
    private Time time;
    private int potsSize;
    private int quantity;

    public OrderDto(int id) {
        this.orderId = id;
    }
}
