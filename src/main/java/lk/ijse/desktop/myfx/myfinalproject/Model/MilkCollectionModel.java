package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.MilkCollectionDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MilkCollectionModel {
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
}
