package com.examination3.address.domain.address;

import static com.examination3.address.domain.exception.ExceptionMessage.ZIP_CODE_7_DIGITS_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.ZIP_CODE_NOT_BLANK_MESSAGE;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * zip code値オブジェクトを表すクラス。
 */
public record ZipCode(String value) {
    /**
     * コンストラクタ。バリデーションを行います。
     *
     * @param value zip codeの値。7桁の数値。
     */
    public ZipCode {
        if (isBlank(value)) {
            throw new IllegalArgumentException(ZIP_CODE_NOT_BLANK_MESSAGE.getMessage());
        }
        if (!isNumeric(value) || value.length() != 7) {
            throw new IllegalArgumentException(ZIP_CODE_7_DIGITS_MESSAGE.getMessage());
        }
    }
}
