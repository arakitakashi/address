package com.examination3.address.presentation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class AddressControllerTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Nested
    class 参照 {
        @Test
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
    }
}
