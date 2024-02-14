package com.examination3.address.domain.address;

import static com.examination3.address.domain.exception.ExceptionMessage.PREFECTURE_MAX_20_DIGITS_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.PREFECTURE_NOT_BLANK_MESSAGE;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * Prefecture値オブジェクトを表すクラス。
 */
public record Prefecture(String value) {
    /**
     * コンストラクタ。値のバリデーションを行います。
     *
     * @param value Prefectureの値。20文字以内の文字列。
     */
    public Prefecture {
       if (isBlank(value)) throw new IllegalArgumentException(PREFECTURE_NOT_BLANK_MESSAGE.getMessage());
       if (value.length() > 20) throw new IllegalArgumentException(PREFECTURE_MAX_20_DIGITS_MESSAGE.getMessage());
    }
}
