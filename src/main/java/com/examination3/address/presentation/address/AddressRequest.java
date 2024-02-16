package com.examination3.address.presentation.address;

/**
 * 入力された住所情報のリクエストボティをマッピングするクラス。
 *
 * @param zipCode       zip codeの値。
 * @param prefecture     prefectureの値。
 * @param city           cityの値。
 * @param streetAddress street addressの値。
 */
public record AddressRequest(
    String zipCode,
    String prefecture,
    String city,
    String streetAddress
) {
}
