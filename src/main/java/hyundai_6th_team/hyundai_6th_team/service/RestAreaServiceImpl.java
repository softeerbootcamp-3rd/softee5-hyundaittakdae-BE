package hyundai_6th_team.hyundai_6th_team.service;


import hyundai_6th_team.hyundai_6th_team.Repository.RestAreaRepository;
import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestAreaServiceImpl implements RestAreaService{

    @Autowired
    private final RestAreaRepository restAreaRepository;
    public RestAreaServiceImpl(RestAreaRepository restAreaRepository){
        this.restAreaRepository = restAreaRepository;
    }

    public RestArea getDetailsOfRestAreaById(Long Id){
        Optional<RestArea> restArea = restAreaRepository.findById(Id);
        if(restArea.isEmpty()){
            throw new RuntimeException("RestArea not exists by id");
        }
        return restArea.get();

    }
}
