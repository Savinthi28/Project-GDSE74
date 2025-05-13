package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.PotsPurchaseDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PotsPurchaseModel {
    public ArrayList<PotsPurchaseDto> viewAllPotsPurchase() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Pots_Purchase_ID");
        ArrayList<PotsPurchaseDto> potsPurchase = new ArrayList<>();
        while (rs.next()) {
            PotsPurchaseDto potsPurchaseDto = new PotsPurchaseDto(
                    rs.getInt("Purchase_ID"),
                    rs.getInt("Pots_Size"),
                    rs.getString("Purchase_Date"),
                    rs.getInt("Quantity"),
                    rs.getDouble("Unit_Price")
            );
            potsPurchase.add(potsPurchaseDto);
        }
        return potsPurchase;
    }
    public boolean savePotsPurchase(PotsPurchaseDto potsPurchaseDto) throws SQLException {
        return CrudUtil.execute(
                "insert into Pots_Purchase_ID values (?,?,?,?,?)",
                potsPurchaseDto.getPurchaseId(),
                potsPurchaseDto.getPotsSize(),
                potsPurchaseDto.getDate(),
                potsPurchaseDto.getQuantity(),
                potsPurchaseDto.getPrice()
        );
    }

    public boolean deletePotsPurchase(PotsPurchaseDto potsPurchaseDto) throws SQLException {
        String sql = "delete from Pots_Purchase_ID where Purchase_ID=?";
        return CrudUtil.execute(sql,potsPurchaseDto.getPurchaseId());
    }
}
