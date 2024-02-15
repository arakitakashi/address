package com.examination3.address.usecase;


/**
 * 住所情報のDtoクラス。ユースケース層からプレゼンテーション層に渡す住所情報を持ちます。
 *
 * @param id IDの値。
 * @param zipCode zip codeの値。
 * @param prefecture prefectureの値。
 * @param city cityの値。
 * @param streetAddress street addressの値。
 */
public record AddressDto(int id, String zipCode, String prefecture, String city, String streetAddress) {
}
