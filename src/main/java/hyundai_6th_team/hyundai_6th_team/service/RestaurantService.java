package hyundai_6th_team.hyundai_6th_team.service;

import hyundai_6th_team.hyundai_6th_team.converter.RestAreaConverter;
import hyundai_6th_team.hyundai_6th_team.dto.response.RestAreaResponse;
import hyundai_6th_team.hyundai_6th_team.entity.Menu;
import hyundai_6th_team.hyundai_6th_team.entity.Rating;
import hyundai_6th_team.hyundai_6th_team.entity.Restaurant;
import hyundai_6th_team.hyundai_6th_team.repository.MenuRepository;
import hyundai_6th_team.hyundai_6th_team.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public List<Menu> findMenu(Long restaurantId){
        return menuRepository.findByRestaurantId(restaurantId);
    }

    public List<RestAreaResponse.RestaurantMenuListDTO> findRestAreaRestaurantMenus(Long restAreaId) {
        List<Restaurant> restaurants = restaurantRepository.findByRestAreaId(restAreaId);

        return restaurants.stream()
                .map(restaurant -> RestAreaConverter.toRestaurantMenuListDTO(restaurant, menuRepository.findByRestaurant(restaurant)))
                .collect(Collectors.toList());
    }

}
