package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.RawMaterialPurchaseDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RawMaterialPurchaseModel {
    public ArrayList<RawMaterialPurchaseDto> viewAllRawMaterialPurchase()throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Raw_Material_Purchase");
        ArrayList<RawMaterialPurchaseDto> rawMaterialPurchase = new ArrayList<>();
        while (rs.next()) {
            RawMaterialPurchaseDto rawMaterialPurchaseDto = new RawMaterialPurchaseDto(
                    rs.getInt("Purchase_ID"),
                    rs.getInt("Supplier_ID"),
                    rs.getString("Material_Name"),
                    rs.getString("Purchase_Date"),
                    rs.getInt("Quantity"),
                    rs.getDouble("Unit_Price")
            );
            rawMaterialPurchase.add(rawMaterialPurchaseDto);
        }
        return rawMaterialPurchase;
    }
    public boolean saveRawMaterialPurchase(RawMaterialPurchaseDto rawMaterialPurchaseDto)throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Raw_Material_Purchase values(?,?,?,?,?,?)",
                rawMaterialPurchaseDto.getPurchaseId(),
                rawMaterialPurchaseDto.getSupplierId(),
                rawMaterialPurchaseDto.getMaterialName(),
                rawMaterialPurchaseDto.getDate(),
                rawMaterialPurchaseDto.getQuantity(),
                rawMaterialPurchaseDto.getUnitPrice()
        );
    }

    public boolean deleteRawMaterialPurchase(RawMaterialPurchaseDto rawMaterialPurchaseDto) throws SQLException{
        String sql = "delete from Raw_Material_Purchase where Purchase_ID=?";
        return CrudUtil.execute(sql,rawMaterialPurchaseDto.getPurchaseId());
    }
}
