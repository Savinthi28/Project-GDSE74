package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RawMaterialPurchaseDto {
    private int purchaseId;
    private int supplierId;
    private String materialName;
    private String Date;
    private int quantity;
    private double unitPrice;
}
