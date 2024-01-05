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

    @Column(nullable = true, length = 30)
    private Long roadId;

    @Column(nullable = false, length = 30)
    private double xpos;

    @Column(nullable = false, length = 30)
    private double ypos;

    @Column(nullable = true, length = 30)
    private String imageUrl;

    @Builder
    public RestArea(Long id, String name, Long roadId, double xpos, double ypos, String imageUrl) {
        this.id = id;
        this.name = name;
        this.roadId = roadId;
        this.xpos = xpos;
        this.ypos = ypos;
        this.imageUrl = imageUrl;
    }
}
