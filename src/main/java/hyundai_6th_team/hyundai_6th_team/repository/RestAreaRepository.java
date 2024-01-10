package hyundai_6th_team.hyundai_6th_team.repository;

import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import hyundai_6th_team.hyundai_6th_team.entity.enums.ReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RestAreaRepository extends JpaRepository<RestArea, Long> {
    @Query("select ra from RestArea ra join Review r on ra.id = r.restArea.id order by ra.posX")
    List<RestArea> findRestAreasOrderByPosX();

    @Query("SELECT ra.id, AVG(r.rating) AS average_rating " +
            "FROM Review r " +
            "JOIN RestArea ra ON r.restArea.id = ra.id " +
            "WHERE r.tag = :tag " +
            "GROUP BY ra.id " +
            "ORDER BY average_rating DESC")
    List<Object[]> findAverageRatingByTag(@Param("tag") ReviewTag tag);
}
