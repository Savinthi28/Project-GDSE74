package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.ReportsDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportsModel {

    public static boolean updateReports(ReportsDto reportsDto) throws SQLException {
        return CrudUtil.execute(
                "update Reports set Report_date = ?, User_ID = ?, Report_Type = ?, Generate_By = ? where Report_ID = ?",
                reportsDto.getDate(),
                reportsDto.getUserId(),
                reportsDto.getReportType(),
                reportsDto.getGenerateBy(),
                reportsDto.getReportId()
        );
    }

    public static ArrayList<Integer> getAllUserId() throws SQLException {
        ResultSet rs = CrudUtil.execute("select DISTINCT User_ID from User");
        ArrayList<Integer> userIds = new ArrayList<>();
        while (rs.next()) {
            Integer userId = rs.getInt(1);
            userIds.add(userId);
        }
        return userIds;
    }

    public String getNextId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Report_ID from Reports order by Report_ID desc limit 1");
        if (resultSet.next()) {
            int lastId = resultSet.getInt(1);
            int nextId = lastId + 1;
            return String.valueOf(nextId);
        }
        return "1";
    }

    public ArrayList<ReportsDto> viewAllReports() throws SQLException , ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Reports");
        ArrayList<ReportsDto> reports = new ArrayList<>();
        while (rs.next()) {
            ReportsDto reportsDto = new ReportsDto(
                    rs.getInt("Report_ID"),
                    rs.getString("Report_date"),
                    rs.getInt("User_ID"),
                    rs.getString("Report_Type"),
                    rs.getString("Generate_By")
            );
            reports.add(reportsDto);
        }
        return reports;
    }
    public boolean saveReport(ReportsDto reportsDto) throws SQLException , ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Reports values (?,?,?,?,?)",
                reportsDto.getReportId(),
                reportsDto.getDate(),
                reportsDto.getUserId(),
                reportsDto.getReportType(),
                reportsDto.getGenerateBy()
        );
    }

    public boolean deleteReports(ReportsDto reportsDto) throws SQLException{
        String sql = "delete from Reports where Report_ID=?";
        return CrudUtil.execute(sql, reportsDto.getReportId());
    }
}
