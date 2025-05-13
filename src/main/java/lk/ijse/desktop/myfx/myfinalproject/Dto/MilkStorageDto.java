package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MilkStorageDto {
    private int storageId;
    private int collectionId;
    private String date;
    private Time duration;
    private double temperature;

    public MilkStorageDto(int id) {
        this.storageId = id;
    }
}
