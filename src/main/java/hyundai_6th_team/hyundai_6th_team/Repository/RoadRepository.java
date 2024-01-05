package hyundai_6th_team.hyundai_6th_team.Repository;

import hyundai_6th_team.hyundai_6th_team.entity.Road;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadRepository extends JpaRepository<Road,Long> {

    public Road findByName(String name);
}
