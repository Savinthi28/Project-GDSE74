package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.PotsInventoryDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PotsInventoryModel {

    public static boolean updatePotsInventory(PotsInventoryDto potsInventoryDto) throws SQLException {
        return CrudUtil.execute(
                "update Pots_Inventory set Quantity = ?, Pots_Size = ?, Condition = ? where Inventory_ID = ?",
                potsInventoryDto.getQuantity(),
                potsInventoryDto.getPotsSize(),
                potsInventoryDto.getCondition(),
                potsInventoryDto.getId()
        );
    }

    public ArrayList<PotsInventoryDto> viewAllPotsInventory() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Pots_Inventory");
        ArrayList<PotsInventoryDto> viewPotsInventory = new ArrayList<>();
        while (rs.next()) {
            PotsInventoryDto viewPotsInventoryDto = new PotsInventoryDto(
            rs.getInt("Inventory_ID"),
            rs.getInt("Quantity"),
            rs.getInt("Pots_Size"),
            rs.getString("Condition")
            );
            viewPotsInventory.add(viewPotsInventoryDto);
        }
        return viewPotsInventory;
    }
    public boolean savePotsInventory(PotsInventoryDto potsInventoryDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Pots_Inventory values (?,?,?,?)",
                potsInventoryDto.getId(),
                potsInventoryDto.getQuantity(),
                potsInventoryDto.getPotsSize(),
                potsInventoryDto.getCondition()
        );
    }

    public boolean deletePotsInventory(PotsInventoryDto potsInventoryDto) throws SQLException{
        String sql = "delete from Pots_Inventory where Inventory_ID=?";
        return CrudUtil.execute(sql,potsInventoryDto.getId());
    }
}
