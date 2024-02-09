package com.examination3.address.domain.address;

import static com.examination3.address.domain.exception.ExceptionMessage.STREET_ADDRESS_MAX_100_DIGITS_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.STREET_ADDRESS_NOT_BLANK_MESSAGE;
import static org.apache.commons.lang3.StringUtils.*;

public record StreetAddress(String value) {

    public StreetAddress {
       if (isBlank(value)) throw new IllegalArgumentException(STREET_ADDRESS_NOT_BLANK_MESSAGE.getMessage());
       if (!(value.length() <= 100)) throw new IllegalArgumentException(STREET_ADDRESS_MAX_100_DIGITS_MESSAGE.getMessage());
    }
}
