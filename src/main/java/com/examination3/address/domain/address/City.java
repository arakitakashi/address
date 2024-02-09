package com.examination3.address.domain.address;

import static com.examination3.address.domain.exception.ExceptionMessage.CITY_MAX_20_DIGITS_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.CITY_NOT_BLANK_MESSAGE;
import static org.apache.commons.lang3.StringUtils.*;

public record City(String value) {

    public City {
        if (isBlank(value)) throw new IllegalArgumentException(CITY_NOT_BLANK_MESSAGE.getMessage());
        if (value.length() > 20) throw new IllegalArgumentException(CITY_MAX_20_DIGITS_MESSAGE.getMessage());
    }
}
