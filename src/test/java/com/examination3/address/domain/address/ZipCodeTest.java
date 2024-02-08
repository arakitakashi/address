package com.examination3.address.domain.address;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipCodeTest {
    @Test
    void _7桁の数値の文字列を渡すと正常にインスタンスが生成される() {
       // execute
       ZipCode actual = new ZipCode("1234567");

       // assert
        assertThat(actual.value()).isEqualTo("1234567");
    }
}
