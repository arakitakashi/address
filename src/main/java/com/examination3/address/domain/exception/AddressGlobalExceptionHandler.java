package com.examination3.address.domain.exception;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AddressGlobalExceptionHandler {
    private static final String KEY_OF_CODE = "code";
    private static final String KEY_OF_MESSAGE = "message";
    private static final String KEY_OF_DETAILS = "details";

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAddressNotFoundException(
        AddressNotFoundException e
    ) {
       Map<String, Object> body = new LinkedHashMap<>();
       body.put(KEY_OF_CODE, "0003");
       body.put(KEY_OF_MESSAGE, e.getMessage());
       body.put(KEY_OF_DETAILS, new ArrayList<>());

       return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
        IllegalArgumentException e
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(KEY_OF_CODE, "0002");
        body.put(KEY_OF_MESSAGE, "request validation error is occurred");
        List<String> details = List.of(e.getMessage());
        body.put(KEY_OF_DETAILS, details);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
