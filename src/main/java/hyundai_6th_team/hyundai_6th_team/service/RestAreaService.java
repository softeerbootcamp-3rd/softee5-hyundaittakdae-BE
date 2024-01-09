package hyundai_6th_team.hyundai_6th_team.service;


import hyundai_6th_team.hyundai_6th_team.Repository.RestAreaRepository;
import hyundai_6th_team.hyundai_6th_team.dto.response.RestAreaResponse;
import hyundai_6th_team.hyundai_6th_team.entity.Amenities;
import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestAreaService {

    private final RestAreaRepository restAreaRepository;

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
                                .amenities(amenities)
                                .build();
    }

}
