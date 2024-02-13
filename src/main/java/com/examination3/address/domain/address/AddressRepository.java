package com.examination3.address.domain.address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    List<Address> findAll();

    Optional<Address> findById(String id);

    Address register(Address address);

    Optional<Address> update(Address address);

    int nextAddressId();
}
