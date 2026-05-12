package dasturlash.uz.entities;

import dasturlash.uz.enums.Role;
import dasturlash.uz.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "profiles")
@Getter
@Setter
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Status status;
    @Column
    private Role role;
    @Column
    private LocalDateTime createdDate;
    @Column
    private Boolean visible = Boolean.TRUE;
}
