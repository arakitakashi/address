package com.examination3.address.domain.address;

import static com.examination3.address.domain.exception.ExceptionMessage.CITY_MAX_20_DIGITS_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.CITY_NOT_BLANK_MESSAGE;
import static org.apache.commons.lang3.StringUtils.isBlank;


/**
 * city値オブジェクトを表すクラス。
 */
public record City(String value) {
    /**
     * コンストラクタ。値のバリデーションを行います。
     *
     * @param value 　cityの値。20文字以下の文字列。
     */
    public City {
        if (isBlank(value)) {
            throw new IllegalArgumentException(CITY_NOT_BLANK_MESSAGE.getMessage());
        }
        if (value.length() > 20) {
            throw new IllegalArgumentException(CITY_MAX_20_DIGITS_MESSAGE.getMessage());
        }
    }
}
