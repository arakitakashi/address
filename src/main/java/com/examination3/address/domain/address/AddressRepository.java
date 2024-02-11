package com.examination3.address.domain.address;

import java.util.List;

public interface AddressRepository {
    List<Address> findAll();
}
