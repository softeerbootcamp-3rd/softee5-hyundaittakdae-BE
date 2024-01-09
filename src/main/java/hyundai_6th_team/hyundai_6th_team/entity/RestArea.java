package hyundai_6th_team.hyundai_6th_team.entity;

import hyundai_6th_team.hyundai_6th_team.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RestArea extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "road_id")
    private Road road;

    @Column(nullable = false)
    private float posX;

    @Column(nullable = false)
    private float posY;

    @Column(nullable = false, length = 15)
    private String phone;

    private String imageUrl;

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}