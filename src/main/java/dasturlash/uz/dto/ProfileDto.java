package dasturlash.uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dasturlash.uz.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDto {
    private Integer id;

    @NotBlank(message = "Ism bo‘sh bo‘lmasligi kerak")
    private String name;

    @NotBlank(message = "Familiya bo‘sh bo‘lmasligi kerak")
    private String surname;

    @NotBlank(message = "Username  bo‘sh bo‘lmasligi kerak")
    private String username;

    @NotBlank(message = "Parol bo‘sh bo‘lmasligi kerak")
    private String password;

    @NotEmpty(message = "Role bo‘sh bo‘lmasligi kerak")
    private List<Role> roleList;

    private LocalDateTime createdDate;
}
