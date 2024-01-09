package hyundai_6th_team.hyundai_6th_team.entity;

import hyundai_6th_team.hyundai_6th_team.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    private String imageUrl;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<Rating> ratingList = new ArrayList<>();

    public float calculateAverageRating() {
        if (ratingList.isEmpty()) {
            return 0.0f; // 평점이 없는 경우 0을 반환
        }

        return (float) ratingList.stream()
                .mapToDouble(Rating::getRating)
                .average()
                .orElse(0.0); // 평균 계산, 값이 없는 경우 0을 반환
    }

}