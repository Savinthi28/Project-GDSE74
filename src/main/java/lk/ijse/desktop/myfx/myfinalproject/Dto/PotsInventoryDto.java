package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PotsInventoryDto {
    private int id;
    private int quantity;
    private int potsSize;
    private String condition;

    public PotsInventoryDto(int id) {
        this.id = id;
    }
}
