package hyundai_6th_team.hyundai_6th_team.service;


import hyundai_6th_team.hyundai_6th_team.apiPayload.ApiResponse;
import hyundai_6th_team.hyundai_6th_team.entity.Review;
import hyundai_6th_team.hyundai_6th_team.entity.enums.ReviewTag;
import hyundai_6th_team.hyundai_6th_team.repository.RestAreaRepository;
import hyundai_6th_team.hyundai_6th_team.dto.response.RestAreaResponse;
import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import hyundai_6th_team.hyundai_6th_team.repository.ReviewRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hyundai_6th_team.hyundai_6th_team.apiPayload.code.statusEnums.ErrorStatus;
import hyundai_6th_team.hyundai_6th_team.apiPayload.exception.handler.GeneralHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestAreaService {

    private final RestAreaRepository restAreaRepository;
    private final ReviewRepository reviewRepository;

    public RestAreaResponse.RestAreaInfoDTO findRestAreaInfo(Long restAreaId) {

        Optional<RestArea> restAreaOptional = restAreaRepository.findById(restAreaId);

        RestArea restArea = restAreaOptional.orElseThrow(() -> new IllegalArgumentException("Rest Area not found By ID"));


        Boolean isNursingRoom;
        Boolean isGasStation;
        Boolean isLPG;
        Boolean isElectric;
        Boolean isTransfer;
        Boolean isPharmacy;

        String amenities = "";
        if(restArea.getAmenities().getIsNursingRoom()) amenities += "수유실";
        if(restArea.getAmenities().getIsGasStation()){
            if(!amenities.isEmpty()) amenities += "|";
            amenities += "주유소";
        }
        if(restArea.getAmenities().getIsLPG()){
            if(!amenities.isEmpty()) amenities += " | ";
            amenities += "LPG";
        }
        if(restArea.getAmenities().getIsElectric()){
            if(!amenities.isEmpty()) amenities += " | ";
            amenities += "전기차충전소";
        }
        if(restArea.getAmenities().getIsTransfer()){
            if(!amenities.isEmpty()) amenities += " | ";
            amenities += "버스환승가능";
        }
        if(restArea.getAmenities().getIsPharmacy()){
            if(!amenities.isEmpty()) amenities += " | ";
            amenities += "약국";
        }

        return RestAreaResponse.RestAreaInfoDTO.builder()
                                .id(restAreaId)
                                .name(restArea.getName())
                                .roadName(restArea.getRoad().getName())
                .imageUrl(restArea.getImageUrl())
                                .amenities(amenities)
                                .build();
    }





    private final AmazonS3Service amazonS3Service;

    @Transactional
    public String uploadImage(MultipartFile image, Long restAreaId) throws IOException {
        RestArea restArea = restAreaRepository.findById(restAreaId).orElseThrow(() ->  new GeneralHandler(ErrorStatus.RESTAREA_NOT_FOUND));
        String imageUrl = amazonS3Service.upload(image, "restArea");
        restArea.setImageUrl(imageUrl);
        restAreaRepository.save(restArea);
        return imageUrl;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Tmp{
        int themeId;
        double rating;
    }
    public ApiResponse findRestAreaList(Long themeNum) {

        List<RestAreaResponse.RestAreaListDto> restAreaDtos = new ArrayList<>();

        final double[] dis = {4.4, 11, 16, 19.2, 21.6};
        int idx = 0;
        if (themeNum == 0) { // 테마 0 이면 거리 내림차순 리턴
            List<RestArea> restAreas = restAreaRepository.findRestAreasOrderByPosX();
            for (RestArea restArea : restAreas) {
                // 해당 휴게소의 가장 높은 테마 평균 값
                List<Review> reviews = reviewRepository.findAllByRestAreaId(restArea.getId());
                Tmp tmp = getHighestTheme(reviews);

                //전체 테마평균 값
                double totalRating = geAvgTotalRating(reviews);

                String themeName = "";
                if (tmp.getThemeId() == 1) {
                    themeName = "음식이 맛있어요";
                }
                if (tmp.getThemeId() == 2) {
                    themeName = "시설이 편리해요";
                }
                if (tmp.getThemeId() == 3) {
                    themeName = "화장실이 깨끗해요";
                }
                if (tmp.getThemeId() == 4) {
                    themeName = "분위기가 특별해요";
                }

                // dtos에 추가
                restAreaDtos.add(RestAreaResponse.RestAreaListDto.builder()
                        .id(restArea.getId()) // 휴게소 id
                        .restAreaName(restArea.getName()) //
                        .totalRating(String.format("%.1f", totalRating)) // 전체 평점
                        .distance(String.valueOf(dis[idx++])) // 거리
                        .themeName(themeName) // 어떤 평점의 평균이 높은지
                        .imageUrl(restArea.getImageUrl())
                        .roadName(restArea.getRoad().getName())
                        .themeRating(String.format("%.1f", tmp.getRating())) // 가장 높은 평점의 평균
                        .build());

            }

        }
        else { // 테마 1~4면 가져온 휴게소의 리뷰=:테마번호의 리뷰값들의 평균 기준 내림차순

            //테마 번호로 조회 후 테마번호의 리뷰값 평균기준으로 내림차순
            ReviewTag reviewTag = ReviewTag.getByIndex(themeNum);
            List<Object[]> rows = restAreaRepository.findAverageRatingByTag(reviewTag);
            for (Object[] row : rows) {
                Long raId = (Long) row[0];
                Double avgRating = (Double) row[1];
                RestArea restArea = restAreaRepository.findById(raId).get();

                String themeName = "";
                if (themeNum == 1) {
                    themeName = "음식이 맛있어요";
                }
                if (themeNum == 2) {
                    themeName = "시설이 편리해요";
                }
                if (themeNum == 3) {
                    themeName = "화장실이 깨끗해요";
                }
                if (themeNum == 4) {
                    themeName = "분위기가 특별해요";
                }

                //전체 평균값
                List<Review> reviews = reviewRepository.findAllByRestAreaId(restArea.getId());
                double totalRating = geAvgTotalRating(reviews);

                // dtos에 추가
                restAreaDtos.add(RestAreaResponse.RestAreaListDto.builder()
                        .id(restArea.getId()) // 휴게소 id
                        .restAreaName(restArea.getName()) // 휴게소 이름
                        .totalRating(String.format("%.1f", totalRating)) // 전체 평점
                        .distance(String.valueOf(dis[idx++])) // 거리
                        .themeName(themeName) // 어떤 평점의 평균이 높은지
                        .imageUrl(restArea.getImageUrl())
                        .roadName(restArea.getRoad().getName())
                        .themeRating(String.format("%.1f", avgRating)) // 가장 높은 평점의 평균
                        .build());
            }


        }

        return ApiResponse.onSuccess(restAreaDtos);
    }

    private double geAvgTotalRating(List<Review> reviews) {
        double sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }

        return sum / reviews.size();
    }

    private Tmp getHighestTheme(List<Review> reviews) {
        double[] sumOfRating = new double[5];
        int[] cnt = new int[5];
        for (Review review : reviews) {
            cnt[review.getTag().ordinal()]++;
            sumOfRating[review.getTag().ordinal()] += review.getRating();
        }

        Tmp tmp = new Tmp();
        double max = -1;
        int maxIdx = -1;
        for (int i = 1; i <= 4; i++) {
            if (max < sumOfRating[i]/cnt[i]) {
                max = sumOfRating[i] / cnt[i];
                maxIdx = i;
            }
        }

        return new Tmp(maxIdx, max);
    }
}
