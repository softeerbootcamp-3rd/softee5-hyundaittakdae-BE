package hyundai_6th_team.hyundai_6th_team.service;

import hyundai_6th_team.hyundai_6th_team.apiPayload.code.statusEnums.ErrorStatus;
import hyundai_6th_team.hyundai_6th_team.apiPayload.exception.handler.GeneralHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    @Override
    public void CheckFlag(Integer flag) {
        if (flag == 1)
            throw new GeneralHandler(ErrorStatus.TEST_EXCEPTION);
    }

}