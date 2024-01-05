package hyundai_6th_team.hyundai_6th_team.controller;

import hyundai_6th_team.hyundai_6th_team.apiPayload.ApiResponse;
import hyundai_6th_team.hyundai_6th_team.converter.TestConverter;
import hyundai_6th_team.hyundai_6th_team.dto.response.TestResponseDTO;
import hyundai_6th_team.hyundai_6th_team.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/")
    public ApiResponse<TestResponseDTO.TestDTO> testAPI(){
        return ApiResponse.onSuccess(TestConverter.toTestDTO());
    }

    @GetMapping("/exception")
    public ApiResponse<TestResponseDTO.TestExceptionDTO> exceptionAPI(@RequestParam Integer flag){
        testService.CheckFlag(flag);
        return ApiResponse.onSuccess(TestConverter.toTestExceptionDTO(flag));
    }
}