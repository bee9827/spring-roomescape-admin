package roomescape.common.exception;

public enum ExceptionMessage {
    RESERVATION_NOT_FOUND_BY_ID("요청된 예약을 찾을 수 없습니다. id: "),
    RESERVATION_TIME_NOT_FOUND_BY_TIME("요청된 예약 시간을 찾을 수 없습니다. time: "),
    ;

    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
