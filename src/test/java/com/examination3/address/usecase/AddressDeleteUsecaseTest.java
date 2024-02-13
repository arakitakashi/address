package com.examination3.address.usecase;

import com.examination3.address.domain.address.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AddressDeleteUsecaseTest {
    @Autowired
    AddressDeleteUsecase sut;

    @Autowired
    AddressRepository addressRepository;

    @Test
    void 指定したIDの住所情報を削除する() {
        // setup
        String addressId = "1";

        // execute
        boolean actual = sut.execute(addressId);

        // assert
        assertThat(actual).isTrue();
    }
}
