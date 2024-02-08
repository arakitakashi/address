package com.examination3.address.domain.address;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ZipCodeTest {
    @Test
    void _7桁の数値の文字列を渡すと正常にインスタンスが生成される() {
        // execute
        ZipCode actual = new ZipCode("1234567");

        // assert
        assertThat(actual.value()).isEqualTo("1234567");
    }

    @ParameterizedTest(name = "{2}の場合")
    @CsvSource(delimiter = '|', textBlock = """
        # VALUE    | MESSAGE                                                  | TESTNAME
          123456   | Error: The Zip code must be numerical and 7 digits long. | zip_codeが6桁の場合
          12345678 | Error: The Zip code must be numerical and 7 digits long. | zip_codeが8桁の場合
          ABCDEFG  | Error: The Zip code must be numerical and 7 digits long. | zip_codeが数値以外の場合
                   | Error: The Zip code must not be null                     | zip_codeがnullの場合
         """)
    void _7桁の数値の文字列以外の値が渡された場合例外が発生する(String value, String message,
        String testName) {
        // assert
        assertThatThrownBy(() -> new ZipCode(value))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message);
    }
}
