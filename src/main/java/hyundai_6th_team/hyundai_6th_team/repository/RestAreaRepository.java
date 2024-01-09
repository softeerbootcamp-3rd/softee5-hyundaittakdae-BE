package hyundai_6th_team.hyundai_6th_team.repository;

import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestAreaRepository extends JpaRepository<RestArea, Long> {

    @Query("select ra from RestArea ra join fetch ra.road join fetch ra.reviews")
    List<RestArea> findRestAreaWithRoadAndRating();
}
