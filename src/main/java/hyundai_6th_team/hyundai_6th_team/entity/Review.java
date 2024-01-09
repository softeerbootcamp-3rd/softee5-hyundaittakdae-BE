package hyundai_6th_team.hyundai_6th_team.entity;

import hyundai_6th_team.hyundai_6th_team.entity.common.BaseEntity;
import hyundai_6th_team.hyundai_6th_team.entity.enums.ReviewTag;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restArea_id")
    private RestArea restArea;

    @Column(nullable = false)
    private float rating;

    @Enumerated(EnumType.STRING)
    private ReviewTag tag;
}
