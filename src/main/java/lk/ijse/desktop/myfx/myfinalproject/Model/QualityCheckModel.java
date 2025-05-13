package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.QualityCheckDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QualityCheckModel {
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
