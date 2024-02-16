package com.examination3.address.domain.exception;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import com.examination3.address.domain.address.AddressRepository;
import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
class AddressGlobalExceptionHandlerTest {
    @MockBean
    AddressRepository addressRepository;
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    void データベース接続に問題が生じた場合InternalServerErrorのレスポンスを返す() throws Exception {
        // setup
        Mockito.when(addressRepository.findAll())
            .thenThrow(DataAccessResourceFailureException.class);

        // assert
        given()
            .when()
            .get("v1/addresses")
            .then()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body("code", is("0001"))
            .body("message", is("A data access error occurred."));
    }
}