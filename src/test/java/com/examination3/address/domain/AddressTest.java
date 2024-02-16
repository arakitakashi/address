package com.examination3.address.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.City;
import com.examination3.address.domain.address.Id;
import com.examination3.address.domain.address.Prefecture;
import com.examination3.address.domain.address.StreetAddress;
import com.examination3.address.domain.address.ZipCode;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AddressTest {

    private static Stream<Arguments> provideInvalidArguments() {
        Id validId = new Id(1);
        ZipCode validZipCode = new ZipCode("1000000");
        Prefecture validPrefecture = new Prefecture("東京都");
        City validCity = new City("千代田区");
        StreetAddress validStreetAddress = new StreetAddress("以下に掲載がない場合");

        return Stream.of(
            Arguments.of(null, validZipCode, validPrefecture, validCity, validStreetAddress,
                "id must not be null."),
            Arguments.of(validId, null, validPrefecture, validCity, validStreetAddress,
                "zip code must not be null."),
            Arguments.of(validId, validZipCode, null, validCity, validStreetAddress,
                "prefecture must not be null."),
            Arguments.of(validId, validZipCode, validPrefecture, null, validStreetAddress,
                "city must not be null."),
            Arguments.of(validId, validZipCode, validPrefecture, validCity, null,
                "street address must not be null.")
        );
    }

    @Test
    void 住所が正常に生成される() {
        // setup
        Id expectedId = new Id(1);
        ZipCode expectedZipCode = new ZipCode("1000000");
        Prefecture expectedPrefecture = new Prefecture("東京都");
        City expectedCity = new City("千代田区");
        StreetAddress expectedStreetAddress = new StreetAddress("以下に掲載がない場合");

        // execute
        Address actual = new Address(
            new Id(1),
            new ZipCode("1000000"),
            new Prefecture("東京都"),
            new City("千代田区"),
            new StreetAddress("以下に掲載がない場合")
        );

        // assert
        assertThat(actual).isNotNull();
        assertThat(actual.id()).isEqualTo(expectedId);
        assertThat(actual.zipCode()).isEqualTo(expectedZipCode);
        assertThat(actual.prefecture()).isEqualTo(expectedPrefecture);
        assertThat(actual.city()).isEqualTo(expectedCity);
        assertThat(actual.streetAddress()).isEqualTo(expectedStreetAddress);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidArguments")
    void 引数がnullの場合に例外が発生する(Id id, ZipCode zipCode, Prefecture prefecture, City city,
        StreetAddress streetAddress, String expectedMessage) {
        assertThatThrownBy(() -> new Address(id, zipCode, prefecture, city, streetAddress))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(expectedMessage);
    }
}
