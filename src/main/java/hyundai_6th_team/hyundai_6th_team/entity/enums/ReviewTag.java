package hyundai_6th_team.hyundai_6th_team.entity.enums;

import java.util.Map;
import java.util.TreeMap;

public enum ReviewTag {
    NONE, FOOD, AMENITIES, RESTROOM, VIBE;


    static public ReviewTag findMinTag(Float food, Float amenities, Float restRoom, Float vibe) {
        Map<Float, ReviewTag> tagMap = new TreeMap<>();

        tagMap.put(food, ReviewTag.FOOD);
        tagMap.put(amenities, ReviewTag.AMENITIES);
        tagMap.put(restRoom, ReviewTag.RESTROOM);
        tagMap.put(vibe, ReviewTag.VIBE);

        // TreeMap을 사용하면 키(값)를 기준으로 정렬되어 있기 때문에 첫 번째 엔트리가 가장 작은 값에 해당함
        return tagMap.entrySet().iterator().next().getValue();
    }

    static public String getReviewTagDescription(ReviewTag reviewTag) {
        if (reviewTag == null) {
            // 처리할 ReviewTag가 없는 경우, 예외처리 또는 기본값 설정 등
            return "해당하는 휴게소 정보가 없습니다.";
        }

        switch (reviewTag) {
            case FOOD:
                return "음식이 맛있는 휴게소";
            case AMENITIES:
                return "시설이 편리한 휴게소";
            case RESTROOM:
                return "화장실이 깨끗한 휴게소";
            case VIBE:
                return "분위기가 특별한 휴게소";
            default:
                // 예외처리 또는 기본값 설정 등
                return "알 수 없는 휴게소 정보";
        }
    }

    public static ReviewTag getByIndex(Long index) {
        if (index == 1) {
            return FOOD;
        } else if (index == 2) {
            return AMENITIES;
        } else if (index == 3) {
            return RESTROOM;
        } else if (index == 4) {
            return VIBE;
        }
        throw new IllegalArgumentException("Invalid index: " + index);
    }
}
