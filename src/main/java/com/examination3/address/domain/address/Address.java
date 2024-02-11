package com.examination3.address.domain.address;

import static com.examination3.address.domain.exception.ExceptionMessage.CITY_NOT_NULL_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.ID_NOT_NULL_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.PREFECTURE_NOT_NULL_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.STREET_ADDRESS_NOT_BLANK_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.STREET_ADDRESS_NOT_NULL_MESSAGE;
import static com.examination3.address.domain.exception.ExceptionMessage.ZIP_CODE_NOT_NULL_MESSAGE;
import static java.util.Objects.isNull;

import com.examination3.address.usecase.AddressDto;

public record Address(
    Id id,
    ZipCode zipCode,
    Prefecture prefecture,
    City city,
    StreetAddress streetAddress
) {
    public Address {
        if (isNull(id)) throw new IllegalArgumentException(ID_NOT_NULL_MESSAGE.getMessage());
        if (isNull(zipCode)) throw new IllegalArgumentException(ZIP_CODE_NOT_NULL_MESSAGE.getMessage());
        if (isNull(prefecture)) throw new IllegalArgumentException(PREFECTURE_NOT_NULL_MESSAGE.getMessage());
        if (isNull(city)) throw new IllegalArgumentException(CITY_NOT_NULL_MESSAGE.getMessage());
        if (isNull(streetAddress)) throw new IllegalArgumentException(STREET_ADDRESS_NOT_NULL_MESSAGE.getMessage());
    }

    public AddressDto toDto() {
        return new AddressDto(id.value(), zipCode().value(), prefecture().value(), city().value(),
            streetAddress().value());
    }
}
