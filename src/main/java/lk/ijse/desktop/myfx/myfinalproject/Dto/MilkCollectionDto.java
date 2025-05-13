package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MilkCollectionDto {
    private int id;
    private String date;
    private double quantity;
    private String buffaloId;

    public MilkCollectionDto(int id) {
        this.id = id;
    }
}
