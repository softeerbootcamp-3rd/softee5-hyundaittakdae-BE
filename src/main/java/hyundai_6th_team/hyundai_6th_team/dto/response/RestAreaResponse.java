package hyundai_6th_team.hyundai_6th_team.dto.response;

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
        String restaurantName;
        List<RestaurantResponse.MenuNameDTO> menuNameDTOList;
    }
}
