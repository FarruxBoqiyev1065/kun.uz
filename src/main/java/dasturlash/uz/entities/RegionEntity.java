package dasturlash.uz.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "regions")
@Getter
@Setter
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer order_number;
    @Column
    private String nameUz;
    @Column
    private String nameRu;
    @Column
    private String nameEn;
    @Column
    private Boolean visible = Boolean.TRUE;
    @Column
    private String key;
    @Column
    private LocalDateTime createdDate;
}
