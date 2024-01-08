package hyundai_6th_team.hyundai_6th_team.service;

import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import org.springframework.http.ResponseEntity;

public interface RestAreaService {

    RestArea getDetailsOfRestAreaById(Long Id);

}
