package hyundai_6th_team.hyundai_6th_team.service;

import hyundai_6th_team.hyundai_6th_team.apiPayload.code.statusEnums.ErrorStatus;
import hyundai_6th_team.hyundai_6th_team.apiPayload.exception.handler.GeneralHandler;
import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import hyundai_6th_team.hyundai_6th_team.repository.RestAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestAreaService {
    private final AmazonS3Service amazonS3Service;
    private final RestAreaRepository restAreaRepository;

    @Transactional
    public String uploadImage(MultipartFile image, Long restAreaId) throws IOException {
        RestArea restArea = restAreaRepository.findById(restAreaId).orElseThrow(() ->  new GeneralHandler(ErrorStatus.RESTAREA_NOT_FOUND));
        String imageUrl = amazonS3Service.upload(image, "restArea");
        restArea.setImageUrl(imageUrl);
        restAreaRepository.save(restArea);
        return imageUrl;
    }
}
