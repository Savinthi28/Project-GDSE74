package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.MilkCollectionDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MilkCollectionModel {

    public static boolean updateMilkCollection(MilkCollectionDto milkCollectionDto) throws SQLException {
        return CrudUtil.execute(
                "update Milk_Collection set Collection_Date = ?, Quantity = ?, Buffalo_ID = ? where Collection_ID = ?",
                milkCollectionDto.getDate(),
                milkCollectionDto.getQuantity(),
                milkCollectionDto.getBuffaloId(),
                milkCollectionDto.getId()
        );
    }

    public ArrayList<MilkCollectionDto> viewAllMilkCollection() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Milk_Collection");
        ArrayList<MilkCollectionDto> milkCollection = new ArrayList<>();
        while (rs.next()) {
            MilkCollectionDto milkCollectionDto = new MilkCollectionDto(
                    rs.getInt("Collection_ID"),
                    rs.getString("Collection_Date"),
                    rs.getDouble("Quantity"),
                    rs.getString("Buffalo_ID")
            );
            milkCollection.add(milkCollectionDto);
        }
        return milkCollection;
    }
    public boolean saveMilkCollection(MilkCollectionDto milkCollection) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Milk_Collection values(?,?,?,?)",
                milkCollection.getId(),
                milkCollection.getDate(),
                milkCollection.getQuantity(),
                milkCollection.getBuffaloId()
        );
    }

    public boolean deleteMikCollection(MilkCollectionDto milkCollectionDto) throws SQLException {
        String sql = "delete from Milk_Collection where Collection_ID=?";
        return CrudUtil.execute(sql, milkCollectionDto.getId());
    }
}
