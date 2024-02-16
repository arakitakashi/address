package com.examination3.address.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import com.examination3.address.domain.address.AddressRepository;
import com.examination3.address.presentation.address.AddressRequest;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import java.sql.DriverManager;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@DBRider
@DBUnit(cacheConnection = false)
class AddressRegisterUsecaseTest {
    private static final String DB_URL =
        "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";
    private static final String DB_USER = "utuser";
    private static final String DB_PASSWORD = "utpassword";

    private static final ConnectionHolder connectionHolder =
        () -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

    @Autowired
    AddressRegisterUsecase sut;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void setUpAll() {
        Flyway.configure().dataSource(DB_URL, DB_USER, DB_PASSWORD).load().migrate();
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("ALTER SEQUENCE ADDRESS_ID_SEQ RESTART WITH 4");
    }

    @AfterEach
    void tearDown() {
        System.out.println(addressRepository.findAll());
        jdbcTemplate.execute("ALTER SEQUENCE ADDRESS_ID_SEQ RESTART WITH 1");
    }

    @Test
    @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
    void 指定した住所情報を新規登録する() {
        // setup
        AddressDto expected =
            new AddressDto(4, "1506001", "東京都", "渋谷区", "恵比寿恵比寿ガーデンプレイス（１階）");

        AddressRequest addressRequest =
            new AddressRequest("1506001", "東京都", "渋谷区", "恵比寿恵比寿ガーデンプレイス（１階）");

        // execute
        AddressDto actual = sut.execute(addressRequest);

        // assert
        assertThat(actual).isEqualTo(expected);
    }
}
