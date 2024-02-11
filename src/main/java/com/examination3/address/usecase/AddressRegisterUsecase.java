package com.examination3.address.usecase;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.City;
import com.examination3.address.domain.address.Id;
import com.examination3.address.domain.address.Prefecture;
import com.examination3.address.domain.address.StreetAddress;
import com.examination3.address.domain.address.ZipCode;
import com.examination3.address.presentation.address.AddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressRegisterUsecase {
    public AddressDto execute(AddressRequest addressRequest) {
        Address address = new Address(
            new Id(4),
            new ZipCode("1506001"),
            new Prefecture("東京都"),
            new City("渋谷区"),
            new StreetAddress("恵比寿恵比寿ガーデンプレイス（１階）")
        );

        return address.toDto();
    }
}
