package hyundai_6th_team.hyundai_6th_team.controller;

import hyundai_6th_team.hyundai_6th_team.apiPayload.ApiResponse;
import hyundai_6th_team.hyundai_6th_team.converter.MenuConverter;
import hyundai_6th_team.hyundai_6th_team.converter.RestAreaConverter;
import hyundai_6th_team.hyundai_6th_team.dto.response.RestAreaResponse;
import hyundai_6th_team.hyundai_6th_team.dto.response.RestaurantResponse;
import hyundai_6th_team.hyundai_6th_team.entity.Menu;
import hyundai_6th_team.hyundai_6th_team.entity.Restaurant;
import hyundai_6th_team.hyundai_6th_team.service.MenuService;
import hyundai_6th_team.hyundai_6th_team.service.RestAreaService;
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
    private final RestAreaService restAreaService;
    private final MenuService menuService;

    @GetMapping("/restaurants/{restaurantId}/menus")
    @Operation(summary = "식당의 인기 메뉴 목록 조회 API",description = "특정 식당의 별점순 상위 3개의 메뉴를 조회하는 API입니다.")
    @Parameters({
            @Parameter(name = "restaurantId", description = "식당Id, path variable 입니다.")
    })
    public ApiResponse<RestaurantResponse.MenuListDTO> getMenuList(@Valid @PathVariable(name = "restaurantId") Long restaurantId){
        List<Menu> menuList = restaurantsService.findMenu(restaurantId);
        return ApiResponse.onSuccess(MenuConverter.toMenuListDTO(menuList));
    }

    @GetMapping("/{restAreaId}/restaurants")
    @Operation(summary = "휴게소 식당목록 및 메뉴목록 조회 API",description = "특정 휴게소의 식당목록과 각 식당의 메뉴목록을 조회하는 API입니다.")
    @Parameters({
            @Parameter(name = "restAreaId", description = "휴게소Id, path variable 입니다.")
    })
    public ApiResponse<List<RestAreaResponse.RestaurantMenuListDTO>> getRestaurantMenuList(@Valid @PathVariable(name = "restAreaId") Long restAreaId){
        return ApiResponse.onSuccess(restaurantsService.findRestAreaRestaurantMenus(restAreaId));
    }


    @GetMapping("/{restAreaId}/details")
    @Operation(summary = "휴게소 상세 조회 API", description = "휴게소 id를 주면 관련 구체적인 정보들을 조회하는 API입니다.")
    @Parameters({
            @Parameter(name = "restAreaId", description = "휴게소Id, path variable 입니다.")
    })
    public ApiResponse<RestAreaResponse.RestAreaInfoDTO> getRestAreaInfo(@Valid @PathVariable(name = "restAreaId") Long restAreaId){

        return ApiResponse.onSuccess(restAreaService.findRestAreaInfo(restAreaId));
    }

    @GetMapping("/{restAreaId}/stores")
    @Operation(summary = "휴게소 인기 메뉴 3개 API", description = "휴게소 id를 주면 관련 구체적인 메뉴 3개 정보들을 조회하는 API입니다.")
    @Parameters({
            @Parameter(name = "restAreaId", description = "휴게소Id, path variable 입니다.")
    })

    public ApiResponse<List<RestaurantResponse.MenuDTO>> findTop3MenuByRestArea(@Valid @PathVariable(name = "restAreaId") Long restAreaId){

        return ApiResponse.onSuccess(menuService.findTop3MenuByRestArea(restAreaId));
    }


}
