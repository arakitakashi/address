package com.examination3.address.domain.exception;

public enum ExceptionMessage {
    ZIP_CODE_NOT_BLANK_MESSAGE("Error: The Zip code must not be null or blank."),
    ZIP_CODE_7_DIGITS_MESSAGE("Error: The Zip code must be numerical and 7 digits long.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
