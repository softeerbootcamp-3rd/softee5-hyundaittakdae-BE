package hyundai_6th_team.hyundai_6th_team.converter;

import hyundai_6th_team.hyundai_6th_team.dto.response.RestAreaResponse;
import hyundai_6th_team.hyundai_6th_team.entity.Menu;
import hyundai_6th_team.hyundai_6th_team.entity.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantConverter {
    public static RestAreaResponse.RestaurantNameDTO toRestaurantNameDTO(Restaurant restaurant) {
        return new RestAreaResponse.RestaurantNameDTO(restaurant.getId(), restaurant.getName());
    }

    public static List<RestAreaResponse.RestaurantNameDTO> toRestaurantNameDTOList(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantConverter::toRestaurantNameDTO)
                .collect(Collectors.toList());
    }
}
