package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.CurdProductionDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CurdProductionModel {
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
