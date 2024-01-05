package hyundai_6th_team.hyundai_6th_team.converter;

import hyundai_6th_team.hyundai_6th_team.dto.response.TestResponseDTO;

public class TestConverter {

    public static TestResponseDTO.TestDTO toTestDTO(){
        return TestResponseDTO.TestDTO.builder()
                .testString("Test")
                .build();
    }

}