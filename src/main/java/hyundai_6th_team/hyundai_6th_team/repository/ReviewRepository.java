package hyundai_6th_team.hyundai_6th_team.repository;

import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import hyundai_6th_team.hyundai_6th_team.entity.Review;
import hyundai_6th_team.hyundai_6th_team.entity.enums.ReviewTag;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r.restArea " +
            "FROM Review r " +
            "WHERE r.tag = :minTag " +
            "GROUP BY r.tag, r.restArea " +
            "ORDER BY AVG(r.rating) DESC")
    List<RestArea> findRestAreaIdWithHighestAverageRating(@Param("minTag") ReviewTag minTag);

    List<Review> findAllByRestAreaId(@Param("restAreaId") Long id);


}
