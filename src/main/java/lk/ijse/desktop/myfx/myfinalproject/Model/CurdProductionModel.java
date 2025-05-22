package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.CurdProductionDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class CurdProductionModel {

    public static boolean updateCurdProduction(CurdProductionDto curdProductionDto) throws SQLException {
        return CrudUtil.execute(
                "update Curd_Production set Production_Date = ?, Expiry_Date = ?, Quantity = ?, Pots_Size = ?, Ingredients = ?, Storage_ID = ? where Production_ID = ?",
                curdProductionDto.getProductionDate(),
                curdProductionDto.getExpiryDate(),
                curdProductionDto.getQuantity(),
                curdProductionDto.getPotsSize(),
                curdProductionDto.getIngredients(),
                curdProductionDto.getStorageId(),
                curdProductionDto.getProductionId()
        );
    }

    public static ArrayList<Integer> getAllPotsSize() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Pots_Size FROM Pots_Inventory");
        HashSet<Integer> uniquePotsSize = new HashSet<>();
        while (rst.next()) {
            Integer pots = rst.getInt(1);
            uniquePotsSize.add(pots);
        }
        return new ArrayList<>(uniquePotsSize);
    }

    public static ArrayList<Integer> getAllStorageId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Storage_ID FROM Milk_Storage");
        HashSet<Integer> storageId = new HashSet<>();
        while (rst.next()) {
            Integer storage = rst.getInt(1);
            storageId.add(storage);
        }
        return new ArrayList<>(storageId);
    }

    public String getNextId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Production_ID from Curd_Production order by Production_ID desc limit 1");
        if (resultSet.next()) {
            int lastId = resultSet.getInt(1);
            int nextId = lastId + 1;
            return String.valueOf(nextId);
        }
        return "1";
    }

    public ArrayList<CurdProductionDto> viewAllCurdProduction() throws ClassNotFoundException, SQLException{
        ResultSet rs = CrudUtil.execute("SELECT * FROM Curd_Production");
        ArrayList<CurdProductionDto> viewCurdProduction = new ArrayList<>();
        while(rs.next()){
            CurdProductionDto curdProductionDto = new CurdProductionDto(
                    rs.getInt("Production_ID"),
                    rs.getString("Production_Date"),
                    rs.getString("Expiry_Date"),
                    rs.getInt("Quantity"),
                    rs.getInt("Pots_Size"),
                    rs.getString("Ingredients"),
                    rs.getInt("Storage_ID")
                    );
            viewCurdProduction.add(curdProductionDto);
        }
        return viewCurdProduction;
    }
    public static boolean saveCurdProduction(CurdProductionDto curdProductionDto) throws ClassNotFoundException, SQLException{
        return CrudUtil.execute(
          "insert into Curd_Production values (?,?,?,?,?,?,?)",
          curdProductionDto.getProductionId(),
          curdProductionDto.getProductionDate(),
          curdProductionDto.getExpiryDate(),
          curdProductionDto.getQuantity(),
          curdProductionDto.getPotsSize(),
          curdProductionDto.getIngredients(),
          curdProductionDto.getStorageId()
        );
    }

    public boolean deleteCurdProduction(CurdProductionDto curdProductionDto) throws SQLException{
        String sql = "delete from Curd_Production where Production_ID=?";
        return CrudUtil.execute(sql, curdProductionDto.getProductionId());
    }
}
