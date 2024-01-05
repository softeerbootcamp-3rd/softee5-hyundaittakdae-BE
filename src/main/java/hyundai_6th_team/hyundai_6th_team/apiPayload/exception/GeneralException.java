package hyundai_6th_team.hyundai_6th_team.apiPayload.exception;

import hyundai_6th_team.hyundai_6th_team.apiPayload.code.BaseErrorCode;
import hyundai_6th_team.hyundai_6th_team.apiPayload.code.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode errorStatus;

    public ErrorDTO getError() {
        return this.errorStatus.getReason();
    }

    public ErrorDTO getErrorHttpStatus(){
        return this.errorStatus.getReasonHttpStatus();
    }
}