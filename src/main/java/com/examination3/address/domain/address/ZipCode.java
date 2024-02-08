package com.examination3.address.domain.address;

import static com.examination3.address.domain.exception.ExceptionMessage.ZIP_CODE_7_DIGITS_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.ZIP_CODE_NOT_BLANK_MESSAGE;
import static org.apache.commons.lang3.StringUtils.*;

public record ZipCode(String value) {
    public ZipCode {
        if (isBlank(value)) throw new IllegalArgumentException(ZIP_CODE_NOT_BLANK_MESSAGE.getMessage());
        if (!isNumeric(value) || value.length() != 7) throw new IllegalArgumentException(ZIP_CODE_7_DIGITS_MESSAGE.getMessage());
    }
}
