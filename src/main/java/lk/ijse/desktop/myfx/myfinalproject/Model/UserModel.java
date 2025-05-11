package lk.ijse.desktop.myfx.myfinalproject.Model;

import lk.ijse.desktop.myfx.myfinalproject.Dto.UserDto;
import lk.ijse.desktop.myfx.myfinalproject.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {
    public ArrayList<UserDto> viewAllUser() throws ClassNotFoundException, SQLException {
       ResultSet rs = CrudUtil.execute("SELECT * FROM User");
        ArrayList<UserDto> user = new ArrayList<>();
        while (rs.next()) {
            UserDto userDto = new UserDto(
                    rs.getInt("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password")
            );
            user.add(userDto);
        }
        return user;
    }
    public boolean saveUser(UserDto userDto) throws ClassNotFoundException, SQLException {
        return CrudUtil.execute(
                "insert into User values (?,?,?)",
                userDto.getId(),
                userDto.getUserName(),
                userDto.getPassword()
        );
    }
}
