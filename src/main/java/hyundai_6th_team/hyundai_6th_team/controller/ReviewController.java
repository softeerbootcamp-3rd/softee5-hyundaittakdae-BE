package hyundai_6th_team.hyundai_6th_team.controller;

import hyundai_6th_team.hyundai_6th_team.apiPayload.ApiResponse;
import hyundai_6th_team.hyundai_6th_team.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResponse addReview(Float food, Float amenities, Float restRoom, Float vibe, @PathVariable Long restAreaId) {

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
    public ApiResponse addReviewAndRating(Float food, Float amenities, Float restRoom, Float vibe,
                                          @PathVariable Long restAreaId,
                                          @PathVariable Long menuId,
                                          Float rating) {
        return reviewService.addReviewAndRating(food, amenities, restRoom, vibe, restAreaId, menuId, rating);
    }
}
