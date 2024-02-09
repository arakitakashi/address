package com.examination3.address.domain.exception;

public enum ExceptionMessage {
    ZIP_CODE_NOT_BLANK_MESSAGE("Error: Zip code must not be null or blank."),
    ZIP_CODE_7_DIGITS_MESSAGE("Error: Zip code must be numerical and 7 digits long."),
    PREFECTURE_NOT_BLANK_MESSAGE("Error: Prefecture must not be null or blank."),
    PREFECTURE_MAX_20_DIGITS_MESSAGE("Error: Prefecture is Max 20 digits."),
    CITY_NOT_BLANK_MESSAGE("Error: City must not be null or blank."),
    CITY_MAX_20_DIGITS_MESSAGE("Error: City is Max 20 digits."),
    STREET_ADDRESS_NOT_BLANK_MESSAGE("Error: Street address must not be null or blank."),
    STREET_ADDRESS_MAX_100_DIGITS_MESSAGE("Error: Street address is Max 100 digits."),
    ID_NOT_NULL_MESSAGE("Error: Id must not be null"),
    ZIP_CODE_NOT_NULL_MESSAGE("Error: Zip code must not be null"),
    PREFECTURE_NOT_NULL_MESSAGE("Error: Prefecture must not be null"),
    CITY_NOT_NULL_MESSAGE("Error: City must not be null"),
    STREET_ADDRESS_NOT_NULL_MESSAGE("Error: Street address must not be null");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
