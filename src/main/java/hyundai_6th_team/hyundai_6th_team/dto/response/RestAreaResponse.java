package hyundai_6th_team.hyundai_6th_team.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class RestAreaResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestaurantMenuListDTO{
        Long restaurantId;
        String restaurantName;
        List<RestaurantResponse.MenuNameDTO> menuNameDTOList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestAreaInfoDTO{
        Long id;
        String imageUrl;
        String roadName;
        String name;
        String amenities;
        
    }
  
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestaurantNameDTO{
        Long restaurantId;
        String restaurantName;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestAreaListDto{
        Long id;
        String imageUrl;
        String distance;
        String themeName;
        String themeRating;
        String restAreaName;
        String roadName;
        String totalRating;
    }

}
