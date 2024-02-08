package com.examination3.address.domain.address;

public record Address(
    Id id,
    ZipCode zipCode,
    Prefecture prefecture,
    City city,
    StreetAddress streetAddress
) {
}
