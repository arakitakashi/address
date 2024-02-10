package com.examination3.address.infrastructure;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.City;
import com.examination3.address.domain.address.Id;
import com.examination3.address.domain.address.Prefecture;
import com.examination3.address.domain.address.StreetAddress;
import com.examination3.address.domain.address.ZipCode;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressRepositoryImplTest {
    @Autowired
    AddressRepository sut;

    @Test
    void 全ての住所情報を取得する() {
        // setup
        Address address1 = new Address(new Id(1), new ZipCode("1000000"), new Prefecture("東京都"), new City("千代田区"), new StreetAddress("以下に掲載がない場合"));
        Address address2 = new Address(new Id(2), new ZipCode("1020072"), new Prefecture("東京都"), new City("千代田区"), new StreetAddress("飯田橋"));
        Address address3 = new Address(new Id(3), new ZipCode("1500043"), new Prefecture("東京都"), new City("渋谷区"), new StreetAddress("道玄坂"));

        List<Address>  expected = List.of(address1, address2, address3);

        // execute
        List<Address> actual = sut.findAll();

        // assert
        assertThat(actual).isEqualTo(expected);
    }
}
