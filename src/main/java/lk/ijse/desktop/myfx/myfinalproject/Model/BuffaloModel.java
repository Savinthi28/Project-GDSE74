package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.BuffaloDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BuffaloModel {
    public ArrayList<BuffaloDto> viewAllBuffalo() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Buffalo");
        ArrayList<BuffaloDto> viewBuffalo = new ArrayList<>();
        while (rs.next()) {
            BuffaloDto buffaloDto = new BuffaloDto(
                    rs.getString("Buffalo_ID"),
                    rs.getDouble("Milk_Production"),
                    rs.getString("Gender"),
                    rs.getInt("Age"),
                            rs.getString("Health_Status")
            );
                    viewBuffalo.add(buffaloDto);
        }
        return viewBuffalo;
    }
    public boolean saveBuffalo(BuffaloDto buffaloDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Buffalo values (?,?,?,?,?)",
                buffaloDto.getBuffaloID(),
                buffaloDto.getMilkProduction(),
                buffaloDto.getGender(),
                buffaloDto.getAge(),
                buffaloDto.getHealthStatus()

        );
    }

    public boolean deleteBuffalo(BuffaloDto buffaloDto) throws SQLException {
        String sql = "delete from Buffalo where Buffalo_ID=?";
        return CrudUtil.execute(sql, buffaloDto.getBuffaloID());
    }
}

