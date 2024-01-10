package hyundai_6th_team.hyundai_6th_team.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ReviewDto {
    String themeString;
    String restAreaName;
    String restAreaImageUrl;
    String roadName;
}
