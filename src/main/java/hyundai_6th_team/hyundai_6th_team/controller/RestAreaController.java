package hyundai_6th_team.hyundai_6th_team.controller;

import hyundai_6th_team.hyundai_6th_team.apiPayload.ApiResponse;
import hyundai_6th_team.hyundai_6th_team.converter.MenuConverter;
import hyundai_6th_team.hyundai_6th_team.dto.response.RestaurantResponse;
import hyundai_6th_team.hyundai_6th_team.entity.Menu;
import hyundai_6th_team.hyundai_6th_team.entity.Restaurant;
import hyundai_6th_team.hyundai_6th_team.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "휴게소", description = "휴게소 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest-areas")
public class RestAreaController {

    private final RestaurantService restaurantsService;

    @GetMapping("/restaurants/{restaurantId}/menus")
    @Operation(summary = "식당의 인기 메뉴 목록 조회 API",description = "특정 식당의 별점순 상위 3개의 메뉴를 조회하는 API입니다.")
    @Parameters({
            @Parameter(name = "restaurantId", description = "식당Id, path variable 입니다.")
    })
    public ApiResponse<RestaurantResponse.MenuListDTO> getMenuList(@Valid @PathVariable(name = "restaurantId") Long restaurantId){
        List<Menu> menuList = restaurantsService.findMenu(restaurantId);
        return ApiResponse.onSuccess(MenuConverter.toMenuListDTO(menuList));
    }
}
