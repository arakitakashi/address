package com.examination3.address.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.AddressRepository;
import com.examination3.address.domain.address.City;
import com.examination3.address.domain.address.Id;
import com.examination3.address.domain.address.Prefecture;
import com.examination3.address.domain.address.StreetAddress;
import com.examination3.address.domain.address.ZipCode;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DBRider
@DBUnit(cacheConnection = false)
class AddressRepositoryImplTest {
    private static final String DB_URL = "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";
    private static final String DB_USER = "utuser";
    private static final String DB_PASSWORD = "utpassword";

    private static final ConnectionHolder connectionHolder =
        () -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

    @Autowired
    AddressRepository sut;

    @BeforeAll
    static void setUpAll() {
        Flyway.configure().dataSource(DB_URL, DB_USER, DB_PASSWORD).load().migrate();
    }

    @Test
    @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/address/addresses-expected.yml")
    void 全ての住所情報を取得する() {
        // setup
        Address address1 = new Address(new Id(1), new ZipCode("1000000"), new Prefecture("東京都"),
            new City("千代田区"), new StreetAddress("以下に掲載がない場合"));
        Address address2 = new Address(new Id(2), new ZipCode("1020072"), new Prefecture("東京都"),
            new City("千代田区"), new StreetAddress("飯田橋"));
        Address address3 = new Address(new Id(3), new ZipCode("1500043"), new Prefecture("東京都"),
            new City("渋谷区"), new StreetAddress("道玄坂"));

        List<Address> expected = List.of(address1, address2, address3);

        // execute
        List<Address> actual = sut.findAll();

        // assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/address/addresses-expected.yml")
    void 指定されたIdの住所情報を取得する() {
        // setup
        Optional<Address> expected = Optional.of(
            new Address(
                new Id(1),
                new ZipCode("1000000"),
                new Prefecture("東京都"),
                new City("千代田区"),
                new StreetAddress("以下に掲載がない場合")
            )
        );

        // execute
        Optional<Address> actual = sut.findById("1");

        // assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/address/addresses-expected.yml")
    void 指定されたIdの住所情報が存在しない場合空のOptionalを返す() {
        // setup
        Optional<Address> expected = Optional.empty();

        // execute
        Optional<Address> actual = sut.findById("99");

        // assert
        assertThat(actual).isEqualTo(expected);
    }

    @Nested
    class 新規登録 {
        @Test
        void 指定した住所情報を新規登録する() {
            // setup
            Address newAddress = new Address(
                new Id(4),
                new ZipCode("1506001"),
                new Prefecture("東京都"),
                new City("渋谷区"),
                new StreetAddress("恵比寿恵比寿ガーデンプレイス（１階）")
            );

            Optional<Address> expected =
                Optional.of(new Address(
                        new Id(4),
                        new ZipCode("1506001"),
                        new Prefecture("東京都"),
                        new City("渋谷区"),
                        new StreetAddress("恵比寿恵比寿ガーデンプレイス（１階）")
                    )
                );

            // execute
            sut.register(newAddress);

            // assert
            Optional<Address> actual = sut.findById("4");
            assertThat(actual).isEqualTo(expected);
        }
    }
}
