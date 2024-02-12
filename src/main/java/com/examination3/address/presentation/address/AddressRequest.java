package com.examination3.address.presentation.address;

import com.examination3.address.domain.address.Address;

public record AddressRequest(String zip_code, String prefecture, String city, String street_address) {
}
