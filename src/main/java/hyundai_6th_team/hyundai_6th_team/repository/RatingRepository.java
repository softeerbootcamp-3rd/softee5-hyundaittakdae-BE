package hyundai_6th_team.hyundai_6th_team.repository;

import hyundai_6th_team.hyundai_6th_team.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

}
