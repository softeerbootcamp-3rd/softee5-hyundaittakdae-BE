package hyundai_6th_team.hyundai_6th_team.Repository;

import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RestAreaRepository extends JpaRepository<RestArea,Long> {
    Optional<RestArea> findById(Long Id);

}
