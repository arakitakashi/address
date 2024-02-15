package com.examination3.address.usecase;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.AddressRepository;
import com.examination3.address.domain.exception.AddressNotFoundException;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import java.sql.DriverManager;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DBRider
@DBUnit(cacheConnection = false)
class AddressDeleteUsecaseTest {
    private static final String DB_URL = "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";
    private static final String DB_USER = "utuser";
    private static final String DB_PASSWORD = "utpassword";

    private static final ConnectionHolder connectionHolder = () -> DriverManager.getConnection(
        DB_URL, DB_USER, DB_PASSWORD);

    @Autowired
    AddressDeleteUsecase sut;

    @Autowired
    AddressRepository addressRepository;

    @Test
    @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
    void 指定したIDの住所情報を削除する() {
        // setup
        String addressId = "1";
        Optional<Address> expected = Optional.empty();

        // execute
        sut.execute(addressId);

        // assert
        Optional<Address> actual = addressRepository.findById(addressId);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
    void 指定したIDの住所情報が存在しない場合例外が発生する() {
        // setup
        String invalidId = "99";

        // assert
        assertThatThrownBy(() -> sut.execute(invalidId))
            .isInstanceOf(AddressNotFoundException.class)
            .hasMessage("specified address [id = 99] is not found.");
    }
}
