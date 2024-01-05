package hyundai_6th_team.hyundai_6th_team.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@Builder
public class RestArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 30)
    private String name;

    @ManyToOne
    @JoinColumn(name = "road_id")
    private Road road;

    @Column(nullable = false, length = 30)
    private double xpos;

    @Column(nullable = false, length = 30)
    private double ypos;

    @Column(nullable = true, length = 30)
    private String imageUrl;

    @Column(nullable = true, length = 100)
    private String tel;

    @Builder
    public RestArea(Long id, String name, Road road, double xpos, double ypos, String imageUrl, String tel) { // 순서 꼭 맞춰줘야 함.... 안맞춰주면 특정 속성 null 들어간다.
        this.id = id;
        this.name = name;
        this.road = road;
        this.xpos = xpos;
        this.ypos = ypos;
        this.tel = tel;
        this.imageUrl = imageUrl;
    }
}
