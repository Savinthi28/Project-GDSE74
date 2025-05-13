package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class StockDto {
    private int stockId;
    private int productionId;
    private String date;
    private int quantity;
    private String stockType;

    public StockDto(int stockId) {
        this.stockId = stockId;
    }
}
