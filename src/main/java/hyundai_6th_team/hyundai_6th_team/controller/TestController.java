package hyundai_6th_team.hyundai_6th_team.controller;

import hyundai_6th_team.hyundai_6th_team.apiPayload.ApiResponse;
import hyundai_6th_team.hyundai_6th_team.converter.TestConverter;
import hyundai_6th_team.hyundai_6th_team.dto.response.TestResponseDTO;
import hyundai_6th_team.hyundai_6th_team.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "테스트", description = "테스트 관련 API")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @Operation(summary = "응답 테스트 API")
    @GetMapping("/")
    public ApiResponse<TestResponseDTO.TestDTO> testAPI(){
        return ApiResponse.onSuccess(TestConverter.toTestDTO());
    }

    @Operation(summary = "예외처리 테스트 API")
    @GetMapping("/exception")
    public ApiResponse<TestResponseDTO.TestExceptionDTO> exceptionAPI(@Parameter(description = "결과 확인용 flag", required = true, example = "1")
                                                                          @RequestParam Integer flag){
        testService.CheckFlag(flag);
        return ApiResponse.onSuccess(TestConverter.toTestExceptionDTO(flag));
    }
}