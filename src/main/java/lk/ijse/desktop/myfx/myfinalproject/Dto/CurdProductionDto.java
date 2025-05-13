package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CurdProductionDto {
    private int productionId;
    private String productionDate;
    private String expiryDate;
    private int quantity;
    private int potsSize;
    private String ingredients;
    private int storageId;

    public CurdProductionDto(int id) {
        this.productionId = id;
    }
}
