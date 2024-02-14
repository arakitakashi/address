package com.examination3.address.domain.exception;

/**
 * 例外メッセージの管理クラス。
 */
public enum ExceptionMessage {
    ADDRESS_NOT_FOUND_MESSAGE("specified address [id = %s] is not found."),
    ZIP_CODE_NOT_BLANK_MESSAGE("zip code must not be blank."),
    ZIP_CODE_7_DIGITS_MESSAGE("zip code must be numerical and 7 digits long."),
    PREFECTURE_NOT_BLANK_MESSAGE("prefecture must not be blank."),
    PREFECTURE_MAX_20_DIGITS_MESSAGE("prefecture is Max 20 digits."),
    CITY_NOT_BLANK_MESSAGE("city must not be blank."),
    CITY_MAX_20_DIGITS_MESSAGE("city is Max 20 digits."),
    STREET_ADDRESS_NOT_BLANK_MESSAGE("street address must not be blank."),
    STREET_ADDRESS_MAX_100_DIGITS_MESSAGE("street address is Max 100 digits."),
    ID_NOT_NULL_MESSAGE("id must not be null."),
    ZIP_CODE_NOT_NULL_MESSAGE("zip code must not be null."),
    PREFECTURE_NOT_NULL_MESSAGE("prefecture must not be null."),
    CITY_NOT_NULL_MESSAGE("city must not be null."),
    STREET_ADDRESS_NOT_NULL_MESSAGE("street address must not be null.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    /**
     * 例外のメッセージを返します。
     *
     * @return 例外メッセージの文字列。
     */
    public String getMessage() {
        return message;
    }

    /**
     * 引数をもとにフォーマットされたメッセージを返します。
     *
     * @param args フォーマットのための引数。
     * @return フォーマットされたメッセージ文字列。
     */
    public String format(Object... args) {
        return String.format(message, args);
    }
}
