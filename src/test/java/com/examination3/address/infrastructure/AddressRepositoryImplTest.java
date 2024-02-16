package com.examination3.address.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@SpringBootTest
@DBRider
@DBUnit(cacheConnection = false)
class AddressRepositoryImplTest {
    private static final String DB_URL =
        "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";
    private static final String DB_USER = "utuser";
    private static final String DB_PASSWORD = "utpassword";
    private static final ConnectionHolder connectionHolder =
        () -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

    @Mock
    Appender<ILoggingEvent> appender;

    @Captor
    ArgumentCaptor<LoggingEvent> captor;

    @Autowired
    AddressRepository sut;

    @BeforeAll
    static void setUpAll() {
        Flyway.configure().dataSource(DB_URL, DB_USER, DB_PASSWORD).load().migrate();
    }

    private AddressRepositoryImpl setupSutWithDatabaseThrowingExceptionByQueryMethod() {
        NamedParameterJdbcTemplate jdbcTemplate = Mockito.mock(NamedParameterJdbcTemplate.class);
        when(jdbcTemplate.query(anyString(), any(DataClassRowMapper.class)))
            .thenThrow(DataAccessResourceFailureException.class);

        AddressRepositoryImpl sut = new AddressRepositoryImpl(jdbcTemplate);
        return sut;
    }

    private AddressRepositoryImpl setupSutWithDatabaseThrowingExceptionByQueryForObjectMethod() {
        NamedParameterJdbcTemplate jdbcTemplate = Mockito.mock(NamedParameterJdbcTemplate.class);
        when(jdbcTemplate.queryForObject(anyString(), anyMap(), any(DataClassRowMapper.class)))
            .thenThrow(DataAccessResourceFailureException.class);

        AddressRepositoryImpl sut = new AddressRepositoryImpl(jdbcTemplate);
        return sut;
    }

    private AddressRepositoryImpl setupSutWithDatabaseThrowingExceptionByUpdateMethod() {
        NamedParameterJdbcTemplate jdbcTemplate = Mockito.mock(NamedParameterJdbcTemplate.class);
        when(jdbcTemplate.update(anyString(), anyMap()))
            .thenThrow(DataAccessResourceFailureException.class);

        AddressRepositoryImpl sut = new AddressRepositoryImpl(jdbcTemplate);
        return sut;
    }

    @Nested
    class 全件取得 {
        @Test
        @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
        @ExpectedDataSet(value = "datasets/address/addresses-expected.yml")
        void 全ての住所情報を取得する() {
            // setup
            Address address1 = new Address(new Id(1), new ZipCode("1000000"),
                new Prefecture("東京都"),
                new City("千代田区"), new StreetAddress("以下に掲載がない場合"));
            Address address2 = new Address(new Id(2), new ZipCode("1020072"),
                new Prefecture("東京都"),
                new City("千代田区"), new StreetAddress("飯田橋"));
            Address address3 = new Address(new Id(3), new ZipCode("1500043"),
                new Prefecture("東京都"),
                new City("渋谷区"), new StreetAddress("道玄坂"));

            List<Address> expected = List.of(address1, address2, address3);

            // execute
            List<Address> actual = sut.findAll();

            // assert
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void データベースとの接続に問題が生じた場合例外が発生する() {
            // setup
            AddressRepositoryImpl sut = setupSutWithDatabaseThrowingExceptionByQueryMethod();

            // assert
            assertThatThrownBy(sut::findAll)
                .isInstanceOf(DataAccessException.class);
        }
    }

    @Nested
    class 単体取得 {
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

        @Test
        void データベースとの接続に問題が生じた場合例外が発生する() {
            // setup
            AddressRepositoryImpl sut =
                setupSutWithDatabaseThrowingExceptionByQueryForObjectMethod();

            // assert
            assertThatThrownBy(() -> sut.findById("1"))
                .isInstanceOf(DataAccessException.class);
        }
    }

    @Nested
    class 新規登録 {
        @Test
        @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
        void 指定した住所情報を新規登録する() {
            // setup
            Address newAddress = createAddressForRegister();

            Optional<Address> expected =
                Optional.of(createAddressForRegister()
                );

            // execute
            sut.register(newAddress);

            // assert
            Optional<Address> actual = sut.findById("4");
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void データベースとの接続に問題が生じた場合例外が発生する() {
            // setup
            AddressRepositoryImpl sut = setupSutWithDatabaseThrowingExceptionByUpdateMethod();
            Address address = createAddressForRegister();

            // assert
            assertThatThrownBy(() -> sut.register(address))
                .isInstanceOf(DataAccessException.class);
        }

        private Address createAddressForRegister() {
            return new Address(
                new Id(4),
                new ZipCode("1506001"),
                new Prefecture("東京都"),
                new City("渋谷区"),
                new StreetAddress("恵比寿恵比寿ガーデンプレイス（１階）")
            );
        }
    }

    @Nested
    class 更新 {
        @Test
        @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
        void 指定した住所情報を更新する() {
            // setup
            Optional<Address> expected =
                Optional.of(
                    createAddressForUpdate(1)
                );

            Address address = createAddressForUpdate(1);

            // execute
            sut.update(address);

            // assert
            Optional<Address> actual = sut.findById("1");
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
        void 指定した住所情報のIDが存在しない場合WARNレベルのログを出力し空のOptionalを返す() {
            // setup
            MockitoAnnotations.openMocks(this);
            Logger logger = (Logger) LoggerFactory.getLogger(AddressRepositoryImpl.class);
            logger.addAppender(appender);
            Address invalidIdAddress = createAddressForUpdate(99);

            Optional<Address> expected = Optional.empty();

            // execute
            Optional<Address> actual = sut.update(invalidIdAddress);

            // assert
            assertLogMessage("WARN", "No Address found with ID: 99");
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void データベースとの接続に問題が生じた場合例外が発生する() {
            // setup
            AddressRepositoryImpl sut = setupSutWithDatabaseThrowingExceptionByUpdateMethod();
            Address address = createAddressForUpdate(1);

            // assert
            assertThatThrownBy(() -> sut.update(address))
                .isInstanceOf(DataAccessException.class);
        }

        private void assertLogMessage(String logLevel, String message) {
            verify(appender).doAppend(captor.capture());
            assertThat(captor.getValue().getLevel()).hasToString(logLevel);
            assertThat(captor.getValue().getMessage()).contains(message);
        }

        private Address createAddressForUpdate(int idValue) {
            return new Address(
                new Id(idValue),
                new ZipCode("1000000"),
                new Prefecture("東京都"),
                new City("千代田区"),
                new StreetAddress("高輪ゲートウェイ")
            );
        }
    }

    @Nested
    class 削除 {
        @Test
        @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
        void 指定したIDの住所情報の削除に成功するとtrueを返す() {
            // setup
            String addressId = "2";

            // execute
            boolean actual = sut.delete(addressId);

            // assert
            assertThat(actual).isTrue();
        }

        @Test
        @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
        void 指定したIDが存在しない場合falseを返す() {
            // setup
            String invalidId = "99";

            // execute
            boolean actual = sut.delete(invalidId);

            // assert
            assertThat(actual).isFalse();
        }

        @Test
        void データベースとの接続に問題が生じた場合例外が発生する() {
            // setup
            AddressRepositoryImpl sut = setupSutWithDatabaseThrowingExceptionByUpdateMethod();

            // assert
            assertThatThrownBy(() -> sut.delete("1"))
                .isInstanceOf(DataAccessException.class);
        }
    }
}
