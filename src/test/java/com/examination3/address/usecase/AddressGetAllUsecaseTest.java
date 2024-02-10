package com.examination3.address.usecase;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class AddressGetAllUsecaseTest {
   @Autowired
   AddressGetAllUsecase sut;

   @Test
    void 全ての住所情報のDtoを返す() {
       // setup
       List<AddressDto> expected = List.of(
           new AddressDto(1, "1000000", "東京都", "千代田区", "以下に掲載がない場合"),
           new AddressDto(2, "1020072", "東京都", "千代田区", "飯田橋"),
           new AddressDto(3, "1500043", "東京都", "渋谷区", "道玄坂")
       );

       // execute
       List<AddressDto> actual = sut.execute();

       // assert
       assertThat(actual).isEqualTo(expected);
   }
}
