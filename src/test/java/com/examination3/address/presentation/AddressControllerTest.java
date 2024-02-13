package com.examination3.address.presentation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.examination3.address.domain.address.AddressRepository;
import com.examination3.address.presentation.address.AddressRequest;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import io.restassured.RestAssured;
import java.sql.DriverManager;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DBRider
@DBUnit(cacheConnection = false)
public class AddressControllerTest {
    private static final String DB_URL = "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";
    private static final String DB_USER = "utuser";
    private static final String DB_PASSWORD = "utpassword";

    private static final ConnectionHolder connectionHolder =
        () -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AddressRepository addressRepository;
    @LocalServerPort
    private int port;

    @BeforeAll
    static void setUpAll() {
        Flyway.configure().dataSource(DB_URL, DB_USER, DB_PASSWORD).load().migrate();
    }

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("ALTER SEQUENCE ADDRESS_ID_SEQ RESTART WITH 4");
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("ALTER SEQUENCE ADDRESS_ID_SEQ RESTART WITH 1");
    }

    @Nested
    class 参照 {
        @Test
        @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
        @ExpectedDataSet(value = "datasets/address/addresses-expected.yml")
        void 全ての書籍情報を取得する() throws Exception {
            // assert
            given()
                .when()
                .get("v1/addresses")
                .then()
                .statusCode(200)
                .body("addresses.size()", is(3))
                .body("addresses[0].id", is("1"))
                .body("addresses[0].zip_code", is("1000000"))
                .body("addresses[0].prefecture", is("東京都"))
                .body("addresses[0].city", is("千代田区"))
                .body("addresses[0].street_address", is("以下に掲載がない場合"))

                .body("addresses[1].id", is("2"))
                .body("addresses[1].zip_code", is("1020072"))
                .body("addresses[1].prefecture", is("東京都"))
                .body("addresses[1].city", is("千代田区"))
                .body("addresses[1].street_address", is("飯田橋"))

                .body("addresses[2].id", is("3"))
                .body("addresses[2].zip_code", is("1500043"))
                .body("addresses[2].prefecture", is("東京都"))
                .body("addresses[2].city", is("渋谷区"))
                .body("addresses[2].street_address", is("道玄坂"));
        }

        @Test
        @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
        @ExpectedDataSet(value = "datasets/address/addresses-expected.yml")
        void 指定したIDの住所情報を取得する() {
            // setup
            String addressId = "1";

            // assert
            given()
                .when()
                .get("/v1/addresses/{id}", addressId)
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", is(addressId))
                .body("zip_code", is("1000000"))
                .body("prefecture", is("東京都"))
                .body("city", is("千代田区"))
                .body("street_address", is("以下に掲載がない場合"));
        }

        @Test
        @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
        @ExpectedDataSet(value = "datasets/address/addresses-expected.yml")
        void 指定したIDの住所情報を存在しない場合例外が発生する() {
            // setup
            String invalidId = "99";

            // assert
            given()
                .when()
                .get("/v1/addresses/{id}", invalidId)
                .then()
                .statusCode(400)
                .assertThat()
                .body("code", equalTo("0003"))
                .body("message", equalTo("specified address [id = 99] is not found."))
                .body("details", empty());
        }

        @Nested
        class 新規登録 {
            @Test
            @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
            void 指定した住所情報を登録する() throws Exception {
                AddressRequest addressRequest =
                    new AddressRequest("1506001", "東京都", "渋谷区", "恵比寿恵比寿ガーデンプレイス（１階）");

                given()
                    .contentType("application/json")
                    .body(addressRequest)
                    .when()
                    .post("/v1/addresses")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .header("Location", containsString("/v1/addresses/4"));
            }

            @ParameterizedTest(name = "{5}の場合")
            @CsvSource(delimiter = '|', textBlock = """
                # ZIP_CODE  | PREFECTURE | CITY  | STREET_ADDRESS                | MESSAGE                           | TEST_NAME
                  ''        | 東京都      | 渋谷区 | 恵比寿恵比寿ガーデンプレイス（１階） | zip code must not be blank.       | zip codeがblank
                            | 東京都      | 渋谷区 | 恵比寿恵比寿ガーデンプレイス（１階） | zip code must not be blank.       | zip codeがnull
                  '1506001' | ''         | 渋谷区 | 恵比寿恵比寿ガーデンプレイス（１階） | prefecture must not be blank.     | prefectureがblank
                  '1506001' |            | 渋谷区 | 恵比寿恵比寿ガーデンプレイス（１階） | prefecture must not be blank.     | prefectureがnull
                  '1506001' | 東京都      | ''    | 恵比寿恵比寿ガーデンプレイス（１階） | city must not be blank.           | cityがblank
                  '1506001' | 東京都      |       | 恵比寿恵比寿ガーデンプレイス（１階） | city must not be blank.           | cityがnull
                  '1506001' | 東京都      | 渋谷区 | ''                             | street address must not be blank. | street addressがblank
                  '1506001' | 東京都      | 渋谷区 |                                | street address must not be blank. | street addressがnull
                """)
            @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
            @ExpectedDataSet(value = "datasets/address/addresses-expected.yml")
            void 指定した住所情報が不正の場合エラーを返す(
                String zipCode, String prefecture, String city, String streetAddress,
                String message, String testName
            ) throws Exception {
                AddressRequest addressRequest = new AddressRequest(zipCode, prefecture, city,
                    streetAddress);
                given()
                    .contentType("application/json")
                    .body(addressRequest)
                    .when()
                    .post("/v1/addresses")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("details[0]", is(message));
            }
        }

        @Nested
        class 更新 {
            @Test
            @DataSet(value = "datasets/address/addresses-setup.yml", cleanBefore = true)
            void 指定したIDの住所情報を更新する() throws Exception {
                AddressRequest addressRequest = new AddressRequest(
                    "1000000",
                    "東京都",
                    "千代田区",
                    "高輪ゲートウェイ"
                );
                given()
                    .contentType("application/json")
                    .body(addressRequest)
                    .when()
                    .patch("/v1/addresses/1")
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
            }
        }
    }
}
