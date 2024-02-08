package com.examination3.address.domain.address;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CityTest {
    @Test
    void _20文字以内の文字列を渡すと正常にインスタンスを生成する() {
        // execute
        City actual = new City("千代田区");

        // assert
        assertThat(actual.value()).isEqualTo("千代田区");
    }

    @ParameterizedTest(name = "{2}の場合")
    @CsvSource(delimiter = '|', textBlock = """
        # VALUE                              | MESSAGE                                    | TEST_NAME 
        '千代田区千代田区千代田区千代田区千代田千代' | Error: The City is Max 20 digits.          | 値が21文字
        ' '                                  | Error: The City must not be null or blank. | 値が空文字
                                             | Error: The City must not be null or blank. | 値がnull 
         """)
    void _20文字以内の文字列以外の値を渡すと例外が発生する(String value, String message, String testName) {
        // assert
        assertThatThrownBy(() -> new City(value))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message);
    }
}
