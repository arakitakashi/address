package com.examination3.address.domain.exception;

public enum ExceptionMessage {
    ZIP_CODE_NOT_BLANK_MESSAGE("Error: The Zip code must not be null or blank."),
    ZIP_CODE_7_DIGITS_MESSAGE("Error: The Zip code must be numerical and 7 digits long."),
    PREFECTURE_NOT_BLANK_MESSAGE("Error: The Prefecture must not be null or blank."),
    PREFECTURE_MAX_20_DIGITS_MESSAGE("Error: The Prefecture is Max 20 digits."),
    CITY_NOT_BLANK_MESSAGE("Error: The City must not be null or blank."),
    CITY_MAX_20_DIGITS_MESSAGE("Error: The City is Max 20 digits.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
