package com.examination3.address.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AddressController {
    /**
     * 全ての住所情報を取得します。
     *
     * @return 書籍のレスポンスデータのリスト
     */
    @GetMapping("/v1/addresses")
    @ResponseStatus(HttpStatus.OK)
    public AddressResponses getAddresses() {
        List<AddressResponse> addressResponses = List.of(
            new AddressResponse("1", "1000000", "東京都", "千代田区", "以下に掲載がない場合"),
            new AddressResponse("2", "1020072", "東京都", "千代田区", "飯田橋"),
            new AddressResponse("3", "1500043", "東京都", "渋谷区", "道玄坂")
        );
        return new AddressResponses(addressResponses);
    }
}
