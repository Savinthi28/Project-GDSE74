package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.StockDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockModel {
    public ArrayList<StockDto> viewAllStock() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Stock");
        ArrayList<StockDto> stocks = new ArrayList<>();
        while (rs.next()) {
            StockDto stock = new StockDto(
                    rs.getInt("Stock_ID"),
                    rs.getInt("Production_ID"),
                    rs.getString("Stock_Date"),
                    rs.getInt("Quantity"),
                    rs.getString("Stock_Type")
            );
            stocks.add(stock);
        }
        return stocks;
    }
    public boolean saveStock(StockDto stockDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Stock values (?,?,?,?,?)",
                stockDto.getStockId(),
                stockDto.getProductionId(),
                stockDto.getDate(),
                stockDto.getQuantity(),
                stockDto.getStockType()
        );
    }

    public boolean deleteStock(StockDto stockDto) throws SQLException {
        String sql = "delete from Stock where Stock_ID=?";
        return CrudUtil.execute(sql, stockDto.getStockId());
    }
}
