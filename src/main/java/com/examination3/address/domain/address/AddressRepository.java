package com.examination3.address.domain.address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    List<Address> findAll();

    Optional<Address> findById(String id);

    int nextAddressId();
}
