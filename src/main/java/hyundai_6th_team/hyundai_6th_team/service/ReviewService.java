package hyundai_6th_team.hyundai_6th_team.service;

import hyundai_6th_team.hyundai_6th_team.apiPayload.ApiResponse;
import hyundai_6th_team.hyundai_6th_team.dto.response.ReviewDto;
import hyundai_6th_team.hyundai_6th_team.entity.Menu;
import hyundai_6th_team.hyundai_6th_team.entity.Rating;
import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import hyundai_6th_team.hyundai_6th_team.entity.Review;
import hyundai_6th_team.hyundai_6th_team.entity.enums.ReviewTag;
import hyundai_6th_team.hyundai_6th_team.repository.MenuRepository;
import hyundai_6th_team.hyundai_6th_team.repository.RatingRepository;
import hyundai_6th_team.hyundai_6th_team.repository.RestAreaRepository;
import hyundai_6th_team.hyundai_6th_team.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RestAreaRepository restAreaRepository;
    private final RatingRepository ratingRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public ApiResponse addReview(Float food, Float amenities, Float restRoom, Float vibe, Long restAreaId) {
        RestArea restArea = restAreaRepository.findById(restAreaId).get();

        addReviewIfNotZero(food, amenities, restRoom, vibe, restArea);

        ReviewTag minTag = ReviewTag.findMinTag(food, amenities, restRoom, vibe);
        List<RestArea> restAreas = reviewRepository.findRestAreaIdWithHighestAverageRating(minTag);
        RestArea findRestArea = restAreas.get(0); // 최상위 값 하나만 필요

        String reviewTagDescription = ReviewTag.getReviewTagDescription(minTag);
        ReviewDto reviewDto = new ReviewDto(reviewTagDescription, findRestArea.getName(), findRestArea.getImageUrl(), findRestArea.getRoad().getName());
        return ApiResponse.onSuccess(reviewDto);
    }

    @Transactional
    public ApiResponse addReviewAndRating(Float food, Float amenities, Float restRoom, Float vibe,
                                          Long restAreaId, Long menuId, Float menuRating) {
        RestArea restArea = restAreaRepository.findById(restAreaId).get();

        addReviewIfNotZero(food, amenities, restRoom, vibe, restArea);

        Menu menu = menuRepository.getById(menuId);
        Rating rating = Rating.builder()
                .menu(menu)
                .rating(menuRating)
                .build();
        ratingRepository.save(rating);

        ReviewTag minTag = ReviewTag.findMinTag(food, amenities, restRoom, vibe);
        List<RestArea> restAreas = reviewRepository.findRestAreaIdWithHighestAverageRating(minTag);
        RestArea findRestArea = restAreas.get(0); // 최상위 값 하나만 필요

        String reviewTagDescription = ReviewTag.getReviewTagDescription(minTag);
        ReviewDto reviewDto = new ReviewDto(reviewTagDescription, findRestArea.getName(), findRestArea.getImageUrl(), findRestArea.getRoad().getName());
        return ApiResponse.onSuccess(reviewDto);
    }

    private void addReviewIfNotZero(Float food, Float amenities, Float restRoom, Float vibe, RestArea restArea) {
        if (food > 0) {
            reviewRepository.save(Review.builder()
                    .tag(ReviewTag.FOOD)
                    .rating(food)
                    .restArea(restArea)
                    .build()
            );
        }
        if (amenities > 0) {
            reviewRepository.save(Review.builder()
                    .tag(ReviewTag.AMENITIES)
                    .rating(amenities)
                    .restArea(restArea)
                    .build()
            );
        }
        if (restRoom > 0) {
            reviewRepository.save(Review.builder()
                    .tag(ReviewTag.RESTROOM)
                    .rating(restRoom)
                    .restArea(restArea)
                    .build()
            );
        }
        if (vibe > 0) {
            reviewRepository.save(Review.builder()
                    .tag(ReviewTag.VIBE)
                    .rating(vibe)
                    .restArea(restArea)
                    .build()
            );
        }
    }

    public int findMinTheme(Float food, Float amenities, Float restRoom, Float vibe) {
        float[] values = {food, amenities, restRoom, vibe};

        float minValue = Float.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < values.length; i++) {
            if (values[i] < minValue) {
                minValue = values[i];
                minIndex = i;
            }
        }

        // 인덱스는 0부터 시작하므로 반환값에 1을 더해줍니다.
        return minIndex + 1;
    }
}
