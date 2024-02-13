package com.examination3.address.usecase;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.AddressRepository;
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
    private final AddressRepository addressRepository;

    public AddressDto execute(AddressRequest addressRequest) {
        int id = addressRepository.nextAddressId();
        System.out.println(id);

        Address registeredAddress = addressRepository.register(
            new Address(
                new Id(id),
                new ZipCode(addressRequest.zip_code()),
                new Prefecture(addressRequest.prefecture()),
                new City(addressRequest.city()),
                new StreetAddress(addressRequest.street_address())
            )
        );

        return registeredAddress.toDto();
    }
}
