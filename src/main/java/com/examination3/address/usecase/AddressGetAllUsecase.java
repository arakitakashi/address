package com.examination3.address.usecase;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.AddressRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressGetAllUsecase {
    private final AddressRepository addressRepository;
    public List<AddressDto> execute() {
        return addressRepository.findAll().stream().map(Address::toDto).toList();
    }
}
