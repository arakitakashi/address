package com.examination3.address.infrastructure;

/**
 * 　住所情報を表すクラス。リポジトリにおいてデータベースから取得した住所情報をマッピングします。
 *
 * @param id             IDの値。
 * @param zip_code       zip codeの値。
 * @param prefecture     prefectureの値。
 * @param city           cityの値。
 * @param street_address street addressの値。
 */
public record AddressRecord(
    int id,
    String zip_code,
    String prefecture,
    String city,
    String street_address) {
}
