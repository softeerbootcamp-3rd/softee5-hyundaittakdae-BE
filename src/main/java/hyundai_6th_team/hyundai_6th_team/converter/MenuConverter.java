package hyundai_6th_team.hyundai_6th_team.converter;

import hyundai_6th_team.hyundai_6th_team.dto.response.RestaurantResponse;
import hyundai_6th_team.hyundai_6th_team.entity.Menu;
import hyundai_6th_team.hyundai_6th_team.entity.Rating;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MenuConverter {
    public static RestaurantResponse.MenuDTO toMenuDTO(Menu menu) {
        return RestaurantResponse.MenuDTO.builder()
                .name(menu.getName())
                .rating(String.format("%.1f", menu.calculateAverageRating()))
                .price(String.valueOf(menu.getPrice()))
                .build();
    }

    public static RestaurantResponse.MenuListDTO toMenuListDTO(List<Menu> menuList) {
        List<RestaurantResponse.MenuDTO> menuDTOList = menuList.stream()
                .map(MenuConverter::toMenuDTO)
                .sorted(Comparator.comparing(RestaurantResponse.MenuDTO::getRating).reversed()) // rating에 따라 내림차순 정렬
                .limit(3) // 상위 3개만 선택
                .collect(Collectors.toList());

        return new RestaurantResponse.MenuListDTO(menuDTOList);
    }
}
