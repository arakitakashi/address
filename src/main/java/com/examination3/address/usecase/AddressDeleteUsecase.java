package com.examination3.address.usecase;

import com.examination3.address.domain.address.AddressRepository;
import com.examination3.address.domain.exception.AddressNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressDeleteUsecase {
    private final AddressRepository addressRepository;
    public void execute(String id) {
        boolean isSuccess = addressRepository.delete(id);
        if (!isSuccess) throw new AddressNotFoundException(id);
    }
}
