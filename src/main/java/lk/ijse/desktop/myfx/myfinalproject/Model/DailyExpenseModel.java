package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.DailyExpenseDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DailyExpenseModel {
    public ArrayList<DailyExpenseDto> viewAllDailyExpense() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Expense");
        ArrayList<DailyExpenseDto> dailyExpenseDto = new ArrayList<>();
        while (rs.next()) {
            DailyExpenseDto temp = new DailyExpenseDto(
                    rs.getInt("Expense_ID"),
                    rs.getString("Expense_Date"),
                    rs.getString("Description"),
                    rs.getDouble("Amount"),
                    rs.getBoolean("Daily_Expense")
            );
            dailyExpenseDto.add(temp);
        }
        return dailyExpenseDto;
    }
    public boolean SavedDailyExpense(DailyExpenseDto expenseDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Expense values (?,?,?,?,?)",
                expenseDto.getId(),
                expenseDto.getDate(),
                expenseDto.getDescription(),
                expenseDto.getAmount(),
                expenseDto.isDailyExpense()
        );
    }
}
