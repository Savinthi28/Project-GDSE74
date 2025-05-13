package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class DailyExpenseDto {
    private int id;
    private String date;
    private String description;
    private double amount;
    private boolean dailyExpense;

    public DailyExpenseDto(int id) {
        this.id = id;
    }
}
