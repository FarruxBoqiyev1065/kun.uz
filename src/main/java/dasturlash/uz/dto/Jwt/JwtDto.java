package dasturlash.uz.dto.Jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtDto {
    private String username;
    private String role;
}
