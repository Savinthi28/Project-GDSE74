package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class DailyIncomeDto {
    private int id;
    private String customerName;
    private String date;
    private String description;
    private double amount;
}
