package hyundai_6th_team.hyundai_6th_team.apiPayload.exception.handler;

import hyundai_6th_team.hyundai_6th_team.apiPayload.code.BaseErrorCode;
import hyundai_6th_team.hyundai_6th_team.apiPayload.exception.GeneralException;

public class GeneralHandler extends GeneralException {

    public GeneralHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}