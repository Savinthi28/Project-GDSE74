package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.DailyIncomeDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DailyIncomeModel {

    public static boolean updateDailyIncome(DailyIncomeDto dailyIncomeDto) throws SQLException {
        return CrudUtil.execute(
                "update Daily_Income set Customer_Name = ?, Income_Date = ?, Description = ?, Amount = ? where Income_ID = ?",
                dailyIncomeDto.getCustomerName(),
                dailyIncomeDto.getDate(),
                dailyIncomeDto.getDescription(),
                dailyIncomeDto.getAmount(),
                dailyIncomeDto.getId()
        );
    }

    public String getNextId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Income_ID from Daily_Income order by Income_ID desc limit 1");
        if (resultSet.next()) {
            int lastId = resultSet.getInt(1);
            int nextId = lastId + 1;
            return String.valueOf(nextId);
        }
        return "1";
    }

    public ArrayList<DailyIncomeDto> viewDailyIncome()throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Daily_Income");
        ArrayList<DailyIncomeDto> dailyIncome = new ArrayList<>();
        while (rs.next()) {
            DailyIncomeDto dailyIncomeDto = new DailyIncomeDto(
                    rs.getInt("Income_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Income_Date"),
                    rs.getString("Description"),
                    rs.getDouble("Amount")
            );
            dailyIncome.add(dailyIncomeDto);
        }
        return dailyIncome;
    }
    public boolean saveDailyIncome(DailyIncomeDto dailyIncomeDto)throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Daily_Income values (?,?,?,?,?)",
                dailyIncomeDto.getId(),
                dailyIncomeDto.getCustomerName(),
                dailyIncomeDto.getDate(),
                dailyIncomeDto.getDescription(),
                dailyIncomeDto.getAmount()
        );
    }

    public boolean deleteDailyIncome(DailyIncomeDto dailyIncomeDto) throws SQLException{
        String sql = "delete from Daily_Income where Income_ID=?";
        return CrudUtil.execute(sql, dailyIncomeDto.getId());
    }
}
