package com.examination3.address.presentation.address;

/**
 * 入力された住所情報のリクエストボティをマッピングするクラス。
 *
 * @param zip_code       zip codeの値。
 * @param prefecture     prefectureの値。
 * @param city           cityの値。
 * @param street_address street addressの値。
 */
public record AddressRequest(
    String zip_code,
    String prefecture,
    String city,
    String street_address
) {
}
