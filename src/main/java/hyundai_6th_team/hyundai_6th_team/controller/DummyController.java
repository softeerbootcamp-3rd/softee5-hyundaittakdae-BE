package hyundai_6th_team.hyundai_6th_team.controller;

import hyundai_6th_team.hyundai_6th_team.apiPayload.ApiResponse;
import hyundai_6th_team.hyundai_6th_team.service.AmazonS3Service;
import hyundai_6th_team.hyundai_6th_team.service.MenuService;
import hyundai_6th_team.hyundai_6th_team.service.RestAreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Tag(name = "더미데이터", description = "더미데이터 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/dummys")
public class DummyController {
    private final RestAreaService restAreaService;
    private final MenuService menuService;

    @PatchMapping(value = "/rest-areas/{restAreaId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "휴게소 사진 업로드 API", description = "더미데이터 저장에 이용되는 휴게소 사진 업로드 API입니다.")
    @Parameters({
            @Parameter(name = "restAreaId", description = "휴게소Id, path variable입니다.")
    })
    public ApiResponse<String> modifyRestAreaImage(@RequestPart(value = "restAreaImage") MultipartFile image,
                                                   @PathVariable(name = "restAreaId") Long restAreaId) throws IOException {
        return ApiResponse.onSuccess(restAreaService.uploadImage(image, restAreaId));
    }

    @PatchMapping(value = "/menus/{menuId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "메뉴 사진 업로드 API", description = "더미데이터 저장에 이용되는 메뉴 사진 업로드 API입니다.")
    @Parameters({
            @Parameter(name = "menuId", description = "메뉴Id, path variable입니다.")
    })
    public ApiResponse<String> modifyMenuImage(@RequestPart(value = "menuImage") MultipartFile image,
                                               @PathVariable(name = "menuId") Long menuId) throws IOException{
        return ApiResponse.onSuccess(menuService.uploadImage(image, menuId));
    }

}
