package com.examination3.address.domain.address;

import static com.examination3.address.domain.exception.ExceptionMessage.PREFECTURE_MAX_20_DIGITS_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.PREFECTURE_NOT_BLANK_MESSAGE;
import static org.apache.commons.lang3.StringUtils.*;

public record Prefecture(String value) {
    public Prefecture {
       if (isBlank(value)) throw new IllegalArgumentException(PREFECTURE_NOT_BLANK_MESSAGE.getMessage());
       if (!(value.length() <= 20)) throw new IllegalArgumentException(PREFECTURE_MAX_20_DIGITS_MESSAGE.getMessage());
    }
}
