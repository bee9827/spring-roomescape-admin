package roomescape.common.exception;

public enum ExceptionMessage {
    RESERVATION_NOT_FOUND("Reservation not found"),
    ;

    private String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
