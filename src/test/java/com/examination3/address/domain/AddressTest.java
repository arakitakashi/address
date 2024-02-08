package com.examination3.address.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AddressTest {

    @Test
    void 住所が正常に生成される() {
        // setup
        Id expectedId = new Id("1");
        ZipCode expectedZipCode = new ZipCode("1000000");
        Prefecture expectedPrefecture = new Prefecture("東京都");
        City expectedCity = new City("千代田区");
        StreetAddress expectedStreetAddress = new StreetAddress("以下に掲載がない場合");

        // execute
        Address actual = new Address(new Id("1"), new ZipCode("1000000"), new Prefecture("東京都"),
            new City("千代田区"), new StreetAddress("以下に掲載がない場合"));

        // assert
        assertThat(actual).isNotNull();
        assertThat(actual.id()).isEqualTo(expectedId);
        assertThat(actual.zip_code()).isEqualTo(expectedZipCode);
        assertThat(actual.prefecture()).isEqualTo(expectedPrefecture);
        assertThat(actual.city()).isEqualTo(expectedCity);
        assertThat(actual.street_address()).isEqualTo(expectedStreetAddress);
    }
}
