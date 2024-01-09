package hyundai_6th_team.hyundai_6th_team.service;

import hyundai_6th_team.hyundai_6th_team.apiPayload.code.statusEnums.ErrorStatus;
import hyundai_6th_team.hyundai_6th_team.apiPayload.exception.handler.GeneralHandler;
import hyundai_6th_team.hyundai_6th_team.entity.Menu;
import hyundai_6th_team.hyundai_6th_team.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final AmazonS3Service amazonS3Service;
    private final MenuRepository menuRepository;

    @Transactional
    public String uploadImage(MultipartFile image, Long menuId) throws IOException {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() ->  new GeneralHandler(ErrorStatus.MENU_NOT_FOUND));
        String imageUrl = amazonS3Service.upload(image, "menu");
        menu.setImageUrl(imageUrl);
        menuRepository.save(menu);
        return imageUrl;
    }
}
