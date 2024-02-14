package com.examination3.address.domain.exception;

import static com.examination3.address.domain.exception.ExceptionMessage.ADDRESS_NOT_FOUND_MESSAGE;

/**
 * 住所情報が見つからない場合にスローされる例外。 この例外は、住所情報の検索操作で対象の住所情報がデータベースに存在しない場合に使用されます。
 */
public class AddressNotFoundException extends RuntimeException {
    /**
     * 指定されたメッセージを持つ{@link AddressNotFoundException}を構築します。
     *
     * @param id 例外に関連するId。
     */
    public AddressNotFoundException(String id) {
        super(ADDRESS_NOT_FOUND_MESSAGE.format(id));
    }
}
