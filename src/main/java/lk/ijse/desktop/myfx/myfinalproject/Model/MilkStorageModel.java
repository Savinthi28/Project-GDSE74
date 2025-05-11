package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.MilkStorageDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MilkStorageModel {
    public ArrayList<MilkStorageDto> viewAllMilkStorage() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Milk_Storage");
        ArrayList<MilkStorageDto> list = new ArrayList<>();
        while (rs.next()) {
            MilkStorageDto milkStorageDto = new MilkStorageDto(
                    rs.getInt("Storage_ID"),
                    rs.getInt("Collection_ID"),
                    rs.getString("Storage_Date"),
                    rs.getTime("Duration"),
                    rs.getDouble("Temperature")
            );
            list.add(milkStorageDto);
        }
        return list;
    }
    public boolean saveMilkStorage(MilkStorageDto milkStorageDto) throws SQLException {
        return CrudUtil.execute(
                "insert into Milk_Storage values(?,?,?,?,?)",
                milkStorageDto.getStorageId(),
                milkStorageDto.getCollectionId(),
                milkStorageDto.getDate(),
                milkStorageDto.getDuration(),
                milkStorageDto.getTemperature()
        );
    }
}
