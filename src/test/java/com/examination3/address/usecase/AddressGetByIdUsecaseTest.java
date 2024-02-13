package com.examination3.address.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import com.examination3.address.domain.exception.AddressNotFoundException;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import java.sql.DriverManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DBRider
@DBUnit(cacheConnection = false)
public class AddressGetByIdUsecaseTest {
    private static final String DB_URL = "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";
    private static final String DB_USER = "utuser";
    private static final String DB_PASSWORD = "utpassword";

    private static final ConnectionHolder connectionHolder = () -> DriverManager.getConnection(
        DB_URL, DB_USER, DB_PASSWORD);

    @Autowired
    AddressGetByIdUsecase sut;

    @Test
    @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/address/addresses-expected.yml")
    void 指定されたIdの住所情報のDtoを返す() {
        // setup
        AddressDto expected = new AddressDto(
            1,
            "1000000",
            "東京都",
            "千代田区",
            "以下に掲載がない場合"
        );

        // execute
        AddressDto actual = sut.execute("1");

        // assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/address/addresses-expected.yml")
    void 指定されたIdの住所情報が存在しない場合例外が発生する() {
        // assert
       assertThatThrownBy(() -> sut.execute("99"))
           .isInstanceOf(AddressNotFoundException.class)
           .hasMessage("specified address [id = 99] is not found.");
    }
}
