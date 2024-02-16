package com.examination3.address.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import java.sql.DriverManager;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DBRider
@DBUnit(cacheConnection = false)
class AddressGetAllUsecaseTest {
    private static final String DB_URL =
        "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";
    private static final String DB_USER = "utuser";
    private static final String DB_PASSWORD = "utpassword";

    private static final ConnectionHolder connectionHolder =
        () -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

    @Autowired
    AddressGetAllUsecase sut;

    @Test
    @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/address/addresses-expected.yml")
    void 全ての住所情報のDtoを返す() {
        // setup
        List<AddressDto> expected = List.of(
            new AddressDto(1, "1000000", "東京都", "千代田区", "以下に掲載がない場合"),
            new AddressDto(2, "1020072", "東京都", "千代田区", "飯田橋"),
            new AddressDto(3, "1500043", "東京都", "渋谷区", "道玄坂")
        );

        // execute
        List<AddressDto> actual = sut.execute();

        // assert
        assertThat(actual).isEqualTo(expected);
    }
}
