package com.examination3.address.presentation.address;

import java.util.List;

/**
 * 出力のための複数の住所情報のレスポンスを格納するクラス。
 *
 * @param addresses 　AddressResponseのリスト。
 */
public record AddressResponses(List<AddressResponse> addresses) {
}
