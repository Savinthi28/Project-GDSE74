package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.SupplierDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public ArrayList<SupplierDto> viewAllSupplier() throws ClassNotFoundException, SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Supplier");
        ArrayList<SupplierDto> viewSupplier = new ArrayList<>();
        while (rs.next()) {
            SupplierDto supplierDto = new SupplierDto(
                    rs.getInt("Supplier_ID"),
                    rs.getString("Supplier_Name"),
                    rs.getString("ContactNumber"),
                    rs.getString("Address")
            );
            viewSupplier.add(supplierDto);
        }
        return viewSupplier;
    }
    public boolean saveSupplier(SupplierDto supplierDto) throws ClassNotFoundException, SQLException {
        return CrudUtil.execute(
                "insert into Supplier values (?,?,?,?)",
                supplierDto.getSupplierId(),
                supplierDto.getSupplierName(),
                supplierDto.getContactNumber(),
                supplierDto.getAddress()
        );
    }
}
