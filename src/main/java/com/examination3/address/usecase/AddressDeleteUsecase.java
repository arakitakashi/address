package com.examination3.address.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressDeleteUsecase {
    public boolean execute(String id) {
        return true;
    }
}
