package hyundai_6th_team.hyundai_6th_team.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import hyundai_6th_team.hyundai_6th_team.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restArea_id")
    @JsonIgnore
    private RestArea restArea;

    @Column(nullable = false, length = 20)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Menu> menus;

}