package hyundai_6th_team.hyundai_6th_team.controller;

import hyundai_6th_team.hyundai_6th_team.apiPayload.ApiResponse;
import hyundai_6th_team.hyundai_6th_team.converter.MenuConverter;
import hyundai_6th_team.hyundai_6th_team.dto.response.RestaurantResponse;
import hyundai_6th_team.hyundai_6th_team.dto.response.ReviewResponse;
import hyundai_6th_team.hyundai_6th_team.entity.Menu;
import hyundai_6th_team.hyundai_6th_team.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "휴게소 후기 API")
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "휴게소 리뷰만 등록")
    @PostMapping("/rest-area/{restAreaId}/review")
    @Parameters({
            @Parameter(name = "food", description = "음식 맛있어요의 평점."),
            @Parameter(name = "amenities", description = "시설이 편리해요의 평점"),
            @Parameter(name = "restRoom", description = "화장실이 깨끗해요의 평점."),
            @Parameter(name = "vibe", description = "분위기가 특별해요의 평점."),
            @Parameter(name = "restAreaId", description = "어떤 휴게소의 평점인지.")
    })
    public ApiResponse addReview(@RequestParam(name = "food") Float food,
                                 @RequestParam(name = "amenities") Float amenities,
                                 @RequestParam(name = "restRoom") Float restRoom,
                                 @RequestParam(name = "vibe") Float vibe,
                                 @PathVariable Long restAreaId) {

        return reviewService.addReview(food, amenities, restRoom, vibe, restAreaId);
    }

    @Operation(summary = "휴게소 리뷰 + 음식 평점 등록")
    @PostMapping("/rest-area/{restAreaId}/reviewAndRating/{menuId}")
    @Parameters({
            @Parameter(name = "food", description = "음식 맛있어요의 평점."),
            @Parameter(name = "amenities", description = "시설이 편리해요의 평점"),
            @Parameter(name = "restRoom", description = "화장실이 깨끗해요의 평점."),
            @Parameter(name = "vibe", description = "분위기가 특별해요의 평점."),
            @Parameter(name = "restAreaId", description = "어떤 휴게소의 평점인지."),
            @Parameter(name = "menuId", description = "어떤 음식의 평점인지."),
            @Parameter(name = "rating", description = "음식의 평점."),
    })
    public ApiResponse addReviewAndRating(@RequestParam(name = "food") Float food,
                                          @RequestParam(name = "amenities") Float amenities,
                                          @RequestParam(name = "restRoom") Float restRoom,
                                          @RequestParam(name = "vibe") Float vibe,
                                          @PathVariable Long restAreaId,
                                          @PathVariable Long menuId,
                                          Float rating) {
        return reviewService.addReviewAndRating(food, amenities, restRoom, vibe, restAreaId, menuId, rating);
    }

    @Operation(summary = "휴게소 후기 조회 API",description = "특정 휴게소의 후기(제일 높은 평점, 제일 낮은 평점, 전체 평점, 전체 후기 수)를 조회하는 API입니다.")
    @GetMapping("/rest-areas/{restAreaId}/reviews")
    @Parameters({
            @Parameter(name = "restAreaId", description = "휴게소Id, path variable 입니다.")
    })
    public ApiResponse<ReviewResponse.RestAreaReviewDTO> getReviewAndRating(@PathVariable(name = "restAreaId") Long restAreaId){
        return ApiResponse.onSuccess(reviewService.getRestAreaReview(restAreaId));
    }
}
