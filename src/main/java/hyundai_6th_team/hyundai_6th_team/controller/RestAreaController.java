package hyundai_6th_team.hyundai_6th_team.controller;


import hyundai_6th_team.hyundai_6th_team.dto.response.RestAreaResponseDTO;
import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import hyundai_6th_team.hyundai_6th_team.service.RestAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RestAreaController {

    @Autowired
    private final RestAreaService restAreaService;
    public RestAreaController(RestAreaService restAreaService){
        this.restAreaService = restAreaService;
    }


    @GetMapping("/rest-areas/{id}/details")
    public ResponseEntity<RestAreaResponseDTO> getRestAreaById(@PathVariable Long Id){

        RestArea restArea = restAreaService.getDetailsOfRestAreaById(Id);
        if(restArea == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RestAreaResponseDTO restAreaResponseDTO = RestAreaResponseDTO.builder()
                .id(restArea.getId())
                .url(restArea.getImageUrl())
                .name(restArea.getName())
                .roadId(restArea.getRoadId())
                .build();
        return new ResponseEntity<>(restAreaResponseDTO, HttpStatus.OK);
    }



}
