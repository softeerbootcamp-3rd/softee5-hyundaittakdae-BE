package hyundai_6th_team.hyundai_6th_team.repository;

import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import hyundai_6th_team.hyundai_6th_team.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByRestAreaId(Long restAreaId);


    @Query(value = "Select * from Restaurant Where rest_area_id = ?1", nativeQuery = true)
    List<Restaurant> findRestaurantByIdFromRestArea(Long restArea_id);

}
