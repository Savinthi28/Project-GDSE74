package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.StockDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockModel {

    public static boolean updateSrock(StockDto stockDto) throws SQLException {
        return CrudUtil.execute(
                "update Stock set Production_ID = ?, Stock_Date = ?, Quantity = ?, Stock_Type = ? where Stock_ID = ?",
                stockDto.getProductionId(),
                stockDto.getDate(),
                stockDto.getQuantity(),
                stockDto.getStockType(),
                stockDto.getStockId()
        );
    }

    public static ArrayList<Integer> getAllProductionId() throws SQLException {
        ResultSet rs = CrudUtil.execute("select Production_ID from Curd_Production");
        ArrayList<Integer> list = new ArrayList<>();
        while (rs.next()) {
            Integer productionId = rs.getInt(1);
            list.add(productionId);
        }
        return list;
    }

    public String getNextId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Stock_ID from Stock order by Stock_ID desc limit 1");
        if (resultSet.next()) {
            int lastId = resultSet.getInt(1);
            int nextId = lastId + 1;
            return String.valueOf(nextId);
        }
        return "1";
    }

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
