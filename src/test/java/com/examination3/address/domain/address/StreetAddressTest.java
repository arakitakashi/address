package com.examination3.address.domain.address;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StreetAddressTest {
    @Test
    void _100桁以内の文字列を渡すと正常にインスタンスを生成する() {
       // execute
       StreetAddress actual = new StreetAddress("飯田橋");

       // assert
        assertThat(actual.value()).isEqualTo("飯田橋");
    }

    @ParameterizedTest(name = "{2}の場合")
    @CsvSource(delimiter = '|', textBlock = """
        # VALUE | MESSAGE                           | TEST_NAME
        ' '     | street address must not be blank. | 値が空文字
                | street address must not be blank. | 値がnull
        """)
    void 空文字もしくはnullを渡すと例外が発生する(String value, String message, String testName) {
        // assert
        assertThatThrownBy(() -> new StreetAddress(value))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message);
    }

    @Test
    void _101文字以上の文字列を渡すと例外が発生する() {
        // setup
        String invalidValue = "あ".repeat(101);

        // assert
        assertThatThrownBy(() -> new StreetAddress(invalidValue))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("street address is Max 100 digits.");
    }
}
