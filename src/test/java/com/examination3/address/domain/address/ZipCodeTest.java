package com.examination3.address.domain.address;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ZipCodeTest {
    @Test
    void _7桁の数値の文字列を渡すと正常にインスタンスが生成される() {
        // execute
        ZipCode actual = new ZipCode("1234567");

        // assert
        assertThat(actual.value()).isEqualTo("1234567");
    }

    @ParameterizedTest(name = "{2}の場合")
    @CsvSource(delimiter = '|', textBlock = """
        # VALUE     | MESSAGE                                       | TESTNAME
          123456    | zip code must be numerical and 7 digits long. | zip_codeが6桁
          12345678  | zip code must be numerical and 7 digits long. | zip_codeが8桁
          ABCDEFG   | zip code must be numerical and 7 digits long. | zip_codeが数値以外
                    | zip code must not be blank.                   | zip_codeがnull
          '       ' | zip code must not be blank.                   | zip_codeが空文字 
         """)
    void _7桁の数値の文字列以外の値が渡された場合例外が発生する(String value, String message,
        String testName) {
        // assert
        assertThatThrownBy(() -> new ZipCode(value))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message);
    }
}
