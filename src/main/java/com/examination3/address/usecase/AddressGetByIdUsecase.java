package com.examination3.address.usecase;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.AddressRepository;
import com.examination3.address.domain.exception.AddressNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressGetByIdUsecase {
    private final AddressRepository addressRepository;

    public AddressDto execute(String id) {
        return addressRepository.findById(id).map(Address::toDto)
            .orElseThrow(() -> new AddressNotFoundException(id));
    }
}
