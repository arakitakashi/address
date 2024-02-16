package com.examination3.address.presentation.address;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 出力のための住所情報のレスポンスボティをマッピングするクラス。
 *
 * @param id            IDの値。
 * @param zipCode       zip codeの値。
 * @param prefecture    prefectureの値。
 * @param city          cityの値。
 * @param streetAddress street addressの値。
 */
public record AddressResponse(
    String id,
    @JsonProperty("zip_code") String zipCode,
    String prefecture,
    String city,
    @JsonProperty("street_address") String streetAddress
) {
}
