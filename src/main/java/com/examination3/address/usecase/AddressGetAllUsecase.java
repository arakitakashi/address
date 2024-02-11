package com.examination3.address.usecase;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.AddressRepository;
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
    private final AddressRepository addressRepository;
    public List<AddressDto> execute() {
        return addressRepository.findAll().stream().map(this::convertToDto).toList();
    }

    private AddressDto convertToDto(Address address) {
        return new AddressDto(address.id().value(), address.zipCode().value(),
            address.prefecture().value(), address.city().value(), address.streetAddress().value());
    }
}
