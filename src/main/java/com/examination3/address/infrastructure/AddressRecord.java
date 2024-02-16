package com.examination3.address.infrastructure;

/**
 * 　住所情報を表すクラス。リポジトリにおいてデータベースから取得した住所情報をマッピングします。
 *
 * @param id             IDの値。
 * @param zipCode       zip codeの値。
 * @param prefecture     prefectureの値。
 * @param city           cityの値。
 * @param streetAddress street addressの値。
 */
public record AddressRecord(
    int id,
    String zipCode,
    String prefecture,
    String city,
    String streetAddress) {
}
