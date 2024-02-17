package com.examination3.address.presentation.address;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 入力された住所情報のリクエストボティをマッピングするクラス。
 *
 * @param zipCode       zip codeの値。
 * @param prefecture     prefectureの値。
 * @param city           cityの値。
 * @param streetAddress street addressの値。
 */
public record AddressRequest(
    @JsonProperty("zip_code") String zipCode,
    @JsonProperty("prefecture") String prefecture,
    @JsonProperty("city") String city,
    @JsonProperty("street_address") String streetAddress
) {
}
