package com.examination3.address.usecase;

import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AddressGetByIdUsecase {
    public Optional<AddressDto> execute(String id) {
        Optional<AddressDto> addressDto = Optional.of(new AddressDto(1, "1000000", "東京都", "千代田区", "以下に掲載がない場合"));
        return addressDto;
    }
}
