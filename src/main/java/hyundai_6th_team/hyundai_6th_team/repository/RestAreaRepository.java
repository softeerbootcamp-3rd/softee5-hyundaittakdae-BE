package hyundai_6th_team.hyundai_6th_team.repository;
import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import hyundai_6th_team.hyundai_6th_team.entity.enums.ReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
4.4
11
16
19.2
21.6
 */
public interface RestAreaRepository extends JpaRepository<RestArea, Long> {
    @Query("select ra from RestArea ra join Review r on ra.id = r.restArea.id order by ra.posX")
    List<RestArea> findRestAreasOrderByPosX();

    @Query("SELECT ra, AVG(r.rating) AS avgRating " +
            "FROM RestArea ra " +
            "JOIN ra.reviews r " +
            "WHERE r.tag = :tag " +
            "GROUP BY ra.id " +
            "ORDER BY avgRating DESC")
    List<Object[]> findRestAreasOrderByRatingAvg(@Param("tag") ReviewTag tag);


}
