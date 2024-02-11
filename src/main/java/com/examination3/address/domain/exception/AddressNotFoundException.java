package com.examination3.address.domain.exception;

import static com.examination3.address.domain.exception.ExceptionMessage.ADDRESS_NOT_FOUND_MESSAGE;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(String id) {
        super(ADDRESS_NOT_FOUND_MESSAGE.format(id));
    }
}
