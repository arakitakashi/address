package com.examination3.address.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.AddressRepository;
import com.examination3.address.domain.address.City;
import com.examination3.address.domain.address.Id;
import com.examination3.address.domain.address.Prefecture;
import com.examination3.address.domain.address.StreetAddress;
import com.examination3.address.domain.address.ZipCode;
import com.examination3.address.domain.exception.AddressNotFoundException;
import com.examination3.address.presentation.address.AddressRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
class AddressUpdateUsecaseTest {
    @Autowired
    AddressUpdateUsecase sut;

    @Autowired
    AddressRepository addressRepository;

    @Test
    void 指定したIDの住所情報を更新する() {
        // setup
        Optional<Address> expected = Optional.of(
            new Address(
                new Id(1),
                new ZipCode("1000000"),
                new Prefecture("東京都"),
                new City("千代田区"),
                new StreetAddress("高輪ゲートウェイ")
            )
        );

        AddressRequest addressRequest = new AddressRequest(
            null,
            null,
            null,
            "高輪ゲートウェイ"
        );

        // execute
        sut.execute("1", addressRequest);

        // assert
        Optional<Address> actual = addressRepository.findById("1");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 指定したIDの住所情報が存在しない場合例外が発生する() {
        // setup
        AddressRequest addressRequest = new AddressRequest(
            null,
            null,
            null,
            "高輪ゲートウェイ"
        );

        // assert
        assertThatThrownBy(() -> sut.execute("99", addressRequest))
            .isInstanceOf(AddressNotFoundException.class)
            .hasMessage("specified address [id = 99] is not found.");
    }
}
