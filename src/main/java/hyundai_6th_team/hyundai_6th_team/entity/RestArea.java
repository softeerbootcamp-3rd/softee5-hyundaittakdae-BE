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

    @Column(nullable = false)
    private String name;

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

    @Builder
    public RestArea(Long id, String name, Road road, float xpos, float ypos, String imageUrl) {
        this.id = id;
        this.name = name;
        this.road = road;
        this.posX = xpos;
        this.posY = ypos;
        this.imageUrl = imageUrl;
    }


    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "amenities_id")
    private Amenities amenities;

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}



