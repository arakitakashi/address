package com.examination3.address.domain.address;

import static com.examination3.address.domain.exception.ExceptionMessage.STREET_ADDRESS_MAX_100_DIGITS_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.STREET_ADDRESS_NOT_BLANK_MESSAGE;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * street address値オブジェクトを表すクラス。
 */
public record StreetAddress(String value) {
    /**
     * コンストラクタ。値のバリデーションを行います。
     *
     * @param value street addressの値。100文字以内の文字列。
     */
    public StreetAddress {
       if (isBlank(value)) throw new IllegalArgumentException(STREET_ADDRESS_NOT_BLANK_MESSAGE.getMessage());
       if (value.length() > 100) throw new IllegalArgumentException(STREET_ADDRESS_MAX_100_DIGITS_MESSAGE.getMessage());
    }
}
