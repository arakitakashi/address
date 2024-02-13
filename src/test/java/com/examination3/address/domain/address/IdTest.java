package com.examination3.address.domain.address;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class IdTest {
    @Test
    void 正の値の数値を渡すと正常にインスタンスが生成される() {
        // execute
        Id actual = new Id(1);

        // assert
       assertThat(actual.value()).isEqualTo(1);
    }
}
