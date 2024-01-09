package hyundai_6th_team.hyundai_6th_team.dto.response;

import lombok.*;

public class ReviewResponse {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestAreaReviewDTO{
        TagReviewDTO highestTag;
        TagReviewDTO lowestTag;
        String totalRating;
        String reviewNum;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TagReviewDTO{
        String tagName;
        String rating;
    }

}
