package hyundai_6th_team.hyundai_6th_team.service;

import hyundai_6th_team.hyundai_6th_team.apiPayload.ApiResponse;
import hyundai_6th_team.hyundai_6th_team.apiPayload.code.statusEnums.ErrorStatus;
import hyundai_6th_team.hyundai_6th_team.apiPayload.exception.handler.GeneralHandler;
import hyundai_6th_team.hyundai_6th_team.dto.response.RestaurantResponse;
import hyundai_6th_team.hyundai_6th_team.dto.response.ReviewDto;
import hyundai_6th_team.hyundai_6th_team.dto.response.ReviewResponse;
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

import java.util.*;
import java.util.stream.Collectors;

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

    public List<ReviewResponse.TagReviewDTO> calculateTagRatings(List<Review> reviews) {
        Map<ReviewTag, Double> averageRatings = Arrays.stream(ReviewTag.values())
                .filter(tag -> tag != ReviewTag.NONE)
                .collect(Collectors.toMap(
                        tag -> tag,
                        tag -> reviews.stream()
                                .filter(review -> review.getTag() == tag)
                                .mapToDouble(Review::getRating)
                                .average()
                                .orElse(0.0)
                ));

        return Arrays.asList(
                new ReviewResponse.TagReviewDTO("음식", String.format("%.1f", averageRatings.get(ReviewTag.FOOD))),
                new ReviewResponse.TagReviewDTO("시설", String.format("%.1f", averageRatings.get(ReviewTag.AMENITIES))),
                new ReviewResponse.TagReviewDTO("화장실", String.format("%.1f", averageRatings.get(ReviewTag.RESTROOM))),
                new ReviewResponse.TagReviewDTO("분위기", String.format("%.1f", averageRatings.get(ReviewTag.VIBE)))
        );
    }

    public ReviewResponse.TagReviewDTO findHighestRatedTag(List<ReviewResponse.TagReviewDTO> tagReviewDTOList) {
        ReviewResponse.TagReviewDTO highestReview = tagReviewDTOList.stream()
                .max(Comparator.comparing(dto -> Double.parseDouble(dto.getRating()))).orElseThrow(() -> new GeneralHandler(ErrorStatus.RESTAREA_REVIEW_NOT_FOUND));

        switch (highestReview.getTagName()) {
            case "음식":
                highestReview.setTagName("음식이 맛있어요");
                break;
            case "시설":
                highestReview.setTagName("시설이 편리해요");
                break;
            case "화장실":
                highestReview.setTagName("화장실이 깨끗해요");
                break;
            case "분위기":
                highestReview.setTagName("분위기가 특별해요");
                break;
            default:
                break;
        }

        return highestReview;
    }

    public ReviewResponse.TagReviewDTO findLowestRatedTag(List<ReviewResponse.TagReviewDTO> tagReviewDTOList) {
        ReviewResponse.TagReviewDTO lowestReview = tagReviewDTOList.stream()
                .min(Comparator.comparing(dto -> Double.parseDouble(dto.getRating()))).orElseThrow(() -> new GeneralHandler(ErrorStatus.RESTAREA_REVIEW_NOT_FOUND));

        switch (lowestReview.getTagName()) {
            case "음식":
                lowestReview.setTagName("음식이 별로예요");
                break;
            case "시설":
                lowestReview.setTagName("시설이 불편해요");
                break;
            case "화장실":
                lowestReview.setTagName("화장실이 더러워요");
                break;
            case "분위기":
                lowestReview.setTagName("분위기가 별로예요");
                break;
            default:
                break;
        }

        return lowestReview;
    }

    public float calculateAverageRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0.0f; // 평점이 없는 경우 0을 반환
        }

        return (float) reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0); // 평균 계산, 값이 없는 경우 0을 반환
    }

    public ReviewResponse.RestAreaReviewDTO getRestAreaReview(Long restAreaId){
        List<Review> reviews = reviewRepository.findByRestAreaId(restAreaId);
        ReviewResponse.TagReviewDTO highest = findHighestRatedTag(calculateTagRatings(reviews));
        ReviewResponse.TagReviewDTO lowest = findLowestRatedTag(calculateTagRatings(reviews));

        RestArea restArea = restAreaRepository.findById(restAreaId).orElseThrow(() -> new GeneralHandler(ErrorStatus.RESTAREA_NOT_FOUND));

        return ReviewResponse.RestAreaReviewDTO.builder()
                .highestTag(highest)
                .lowestTag(lowest)
                .totalRating(String.format("%.1f", calculateAverageRating(reviews)))
                .reviewNum(String.valueOf(reviews.size()))
                .build();
    }

}