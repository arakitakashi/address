package com.examination3.address.domain.address;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PrefectureTest {
    @Test
    void _20文字以内の文字列を渡すと正常にインスタンスを生成する() {
        // execute
        Prefecture actual = new Prefecture("東京都");

        // assert
        assertThat(actual.value()).isEqualTo("東京都");
    }

    @ParameterizedTest(name = "{2}の場合")
    @CsvSource(delimiter = '|', textBlock = """
        # VALUE | MESSAGE | TEST_NAME
        '東京都東京都東京都東京都東京都東京都東京都' | Error: Max 20 chars. | 値が21文字
        ' ' | Error: Prefecture must not be blank or null | 値が空文字
            |Error: Prefecture must not be blank or null | 値がnull 
        """)
    void _20文字以内の文字列以外の値を渡した場合例外が発生する(String value, String message, String testName) {
        // assert
        assertThatThrownBy(() -> new Prefecture(value))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message);
    }
}
