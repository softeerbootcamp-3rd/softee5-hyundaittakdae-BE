package hyundai_6th_team.hyundai_6th_team.converter;

import hyundai_6th_team.hyundai_6th_team.dto.response.RestAreaResponse;
import hyundai_6th_team.hyundai_6th_team.dto.response.RestaurantResponse;
import hyundai_6th_team.hyundai_6th_team.entity.Menu;
import hyundai_6th_team.hyundai_6th_team.entity.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class RestAreaConverter {

    public static RestAreaResponse.RestaurantMenuListDTO toRestaurantMenuListDTO(Restaurant restaurant, List<Menu> menuList) {
        List<RestaurantResponse.MenuNameDTO> menuNameDTOList = menuList.stream()
                .map(MenuConverter::toMenuNameDTO)
                .collect(Collectors.toList());

        return RestAreaResponse.RestaurantMenuListDTO.builder()
                .restaurantId(restaurant.getId())
                .restaurantName(restaurant.getName())
                .menuNameDTOList(menuNameDTOList)
                .build();
    }

}
