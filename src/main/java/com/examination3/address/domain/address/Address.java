package com.examination3.address.domain.address;

import static com.examination3.address.domain.exception.ExceptionMessage.CITY_NOT_NULL_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.ID_NOT_NULL_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.PREFECTURE_NOT_NULL_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.STREET_ADDRESS_NOT_NULL_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.ZIP_CODE_NOT_NULL_MESSAGE;
import static java.util.Objects.isNull;

import com.examination3.address.usecase.AddressDto;

/**
 * 住所エンティティを表すクラス。　住所の基本情報であるID、ZIP CODE、Prefecture、City、Street Addressを保持します。
 */
public record Address(
    Id id,
    ZipCode zipCode,
    Prefecture prefecture,
    City city,
    StreetAddress streetAddress
) {
    /**
     * 住所エンティティのコンストラクタ。　住所の基本情報であるID、ZIP CODE、Prefecture、City、Street Addressを保持します。
     *
     * @param id            IDの値オブジェクト。　nullであってはいけません。
     * @param zipCode       Zip Codeの値オブジェクト。 nullであってはいけません。
     * @param prefecture    Prefectureの値オブジェクト。　nullであってはいけません。
     * @param city          Cityの値オブジェクト。　nullであってはいけません。
     * @param streetAddress Street Addressの値オブジェクト。　nullであってはいけません。
     */
    public Address {
        if (isNull(id)) {
            throw new IllegalArgumentException(ID_NOT_NULL_MESSAGE.getMessage());
        }
        if (isNull(zipCode)) {
            throw new IllegalArgumentException(ZIP_CODE_NOT_NULL_MESSAGE.getMessage());
        }
        if (isNull(prefecture)) {
            throw new IllegalArgumentException(PREFECTURE_NOT_NULL_MESSAGE.getMessage());
        }
        if (isNull(city)) {
            throw new IllegalArgumentException(CITY_NOT_NULL_MESSAGE.getMessage());
        }
        if (isNull(streetAddress)) {
            throw new IllegalArgumentException(STREET_ADDRESS_NOT_NULL_MESSAGE.getMessage());
        }
    }

    /**
     * AddressオブジェクトをDTOに変換します。
     *
     * @return AddressDtoオブジェクト。
     */
    public AddressDto toDto() {
        return new AddressDto(id.value(), zipCode().value(), prefecture().value(), city().value(),
            streetAddress().value());
    }
}
