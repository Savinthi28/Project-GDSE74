package lk.ijse.desktop.myfx.myfinalproject.Dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UserDto {
    private int id;
    private String userName;
    private String password;

    public UserDto(int id) {
        this.id = id;
    }
}
