package dasturlash.uz.dto;

import dasturlash.uz.enums.Role;
import dasturlash.uz.enums.Status;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileDto {
    private Integer id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
//    private Status status;
//    private List<Role> role;
}
