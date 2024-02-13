package com.examination3.address.usecase;

import static org.apache.commons.lang3.StringUtils.isBlank;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.AddressRepository;
import com.examination3.address.domain.address.City;
import com.examination3.address.domain.address.Prefecture;
import com.examination3.address.domain.address.StreetAddress;
import com.examination3.address.domain.address.ZipCode;
import com.examination3.address.domain.exception.AddressNotFoundException;
import com.examination3.address.presentation.address.AddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressUpdateUsecase {
    private final AddressRepository addressRepository;

    public void execute(String id, AddressRequest addressRequest) {
        Address existingAddress = addressRepository.findById(id)
            .orElseThrow(() -> new AddressNotFoundException(id));
        Address address = requestToAddress(existingAddress, addressRequest);
        addressRepository.update(address);
    }

    private Address requestToAddress(Address existingAddress, AddressRequest addressRequest) {
       ZipCode updateZipCode =
       !isBlank(addressRequest.zip_code()) ? new ZipCode(addressRequest.zip_code()) : existingAddress.zipCode();

       Prefecture updatePrefecture =
           !isBlank(addressRequest.prefecture()) ? new Prefecture(addressRequest.prefecture()) : existingAddress.prefecture();

       City updateCity =
           !isBlank(addressRequest.city()) ? new City(addressRequest.city()) : existingAddress.city();

       StreetAddress updateStreetAddress =
           !isBlank(addressRequest.street_address()) ? new StreetAddress(addressRequest.street_address()) : existingAddress.streetAddress();

       return new Address(
           existingAddress.id(),
           updateZipCode,
           updatePrefecture,
           updateCity,
           updateStreetAddress
       );
    }
}
