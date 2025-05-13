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
