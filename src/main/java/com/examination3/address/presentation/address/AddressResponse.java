package com.examination3.address.presentation.address;

/**
 * 出力のための住所情報のレスポンスボティをマッピングするクラス。
 *
 * @param id             idの値。
 * @param zip_code       zip codeの値。
 * @param prefecture     prefectureの値。
 * @param city           cityの値。
 * @param street_address street addressの値。
 */
public record AddressResponse(
    String id,
    String zip_code,
    String prefecture,
    String city,
    String street_address
) {
}
