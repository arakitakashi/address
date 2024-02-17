package com.examination3.address.domain.exception;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import com.examination3.address.domain.address.AddressRepository;
import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
class AddressGlobalExceptionHandlerTest {
    @Mock
    Appender<ILoggingEvent> appender;

    @Captor
    ArgumentCaptor<LoggingEvent> captor;
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

    @Test
    void 予期しない例外が発生した場合InternalServerErrorのレスポンスを返す() throws Exception {
        // setup
        Mockito.when(addressRepository.findAll())
            .thenThrow(NullPointerException.class);

        // assert
        given()
            .when()
            .get("v1/addresses")
            .then()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body("code", is("9999"))
            .body("message", is("a system error has occurred."));
    }

    @Test
    void 予期しない例外が発生した場合ログを出力する() throws Exception {
        // setup
        MockitoAnnotations.openMocks(this);
        Logger logger = (Logger) LoggerFactory.getLogger(AddressGlobalExceptionHandler.class);
        logger.addAppender(appender);

        Mockito.when(addressRepository.findAll())
            .thenThrow(NullPointerException.class);

        // assert
        given()
            .when()
            .get("v1/addresses")
            .then()
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        assertLogMessage();
    }

    private void assertLogMessage() {
        verify(appender).doAppend(captor.capture());
        assertThat(captor.getValue().getLevel()).hasToString("ERROR");
        assertThat(captor.getValue().getMessage()).contains("an unexpected error has occurred.");
    }
}