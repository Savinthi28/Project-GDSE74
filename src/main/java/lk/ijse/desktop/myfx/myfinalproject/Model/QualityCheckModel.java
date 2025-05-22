package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.QualityCheckDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QualityCheckModel {

    public static boolean updateQualityCheck(QualityCheckDto qualityCheckDto) throws SQLException {
        return CrudUtil.execute(
                "update Quality_Check set Collection_ID = ?, Appearance = ?, Fat_Content = ?, Temperature = ?, Check_Date = ?, Notes = ? where Check_ID = ?",
                qualityCheckDto.getCollectionId(),
                qualityCheckDto.getAppearance(),
                qualityCheckDto.getFatContent(),
                qualityCheckDto.getTemperature(),
                qualityCheckDto.getDate(),
                qualityCheckDto.getNotes(),
                qualityCheckDto.getCheckId()
        );
    }

    public static ArrayList<Integer> getAllQualityCollectionId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT DISTINCT Collection_ID FROM Milk_Collection");
        ArrayList<Integer> qualityCollectionIds = new ArrayList<>();
        while (rst.next()) {
            Integer collectionId = rst.getInt(1);
            qualityCollectionIds.add(collectionId);
        }
        return qualityCollectionIds;
    }

    public String getNextId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Check_ID from Quality_Check order by Check_ID desc limit 1");
        if (resultSet.next()) {
            int lastId = resultSet.getInt(1);
            int nextId = lastId + 1;
            return String.valueOf(nextId);
        }
        return "1";
    }

    public ArrayList<QualityCheckDto> viewAllQualityCheck() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Quality_Check");
        ArrayList<QualityCheckDto> qualityCheck = new ArrayList<>();
        while (rs.next()) {
            QualityCheckDto qualityCheckDto = new QualityCheckDto(
                    rs.getInt("Check_ID"),
                    rs.getInt("Collection_ID"),
                    rs.getString("Appearance"),
                    rs.getDouble("Fat_Content"),
                    rs.getDouble("Temperature"),
                    rs.getString("Check_Date"),
                    rs.getString("Notes")
            );
            qualityCheck.add(qualityCheckDto);
        }
        return qualityCheck;
    }
    public boolean saveQualityCheck(QualityCheckDto qualityCheckDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Quality_Check values (?,?,?,?,?,?,?)",
                qualityCheckDto.getCheckId(),
                qualityCheckDto.getCollectionId(),
                qualityCheckDto.getAppearance(),
                qualityCheckDto.getFatContent(),
                qualityCheckDto.getTemperature(),
                qualityCheckDto.getDate(),
                qualityCheckDto.getNotes()
        );
    }

    public boolean deleteQualityCheck(QualityCheckDto qualityCheckDto) throws SQLException {
        String sql = "delete from Quality_Check where Check_ID=?";
        return CrudUtil.execute(sql, qualityCheckDto.getCheckId());
    }
}
