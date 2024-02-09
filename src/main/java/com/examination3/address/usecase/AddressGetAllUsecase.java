package com.examination3.address.usecase;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.City;
import com.examination3.address.domain.address.Id;
import com.examination3.address.domain.address.Prefecture;
import com.examination3.address.domain.address.StreetAddress;
import com.examination3.address.domain.address.ZipCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressGetAllUsecase {
    public List<AddressDto> execute() {
        List<Address> addresses = List.of(
            new Address(new Id(1), new ZipCode("1000000"), new Prefecture("東京都"),
                new City("千代田区"), new StreetAddress("以下に掲載がない場合")),
            new Address(new Id(2), new ZipCode("1020072"), new Prefecture("東京都"),
                new City("千代田区"), new StreetAddress("飯田橋")),
            new Address(new Id(3), new ZipCode("1500043"), new Prefecture("東京都"),
                new City("渋谷区"), new StreetAddress("道玄坂"))
        );

        return addresses.stream().map(this::convertToDto).toList();
    }

    private AddressDto convertToDto(Address address) {
        return new AddressDto(address.id().value(), address.zipCode().value(),
            address.prefecture().value(), address.city().value(), address.streetAddress().value());
    }
}
