package com.examination3.address.presentation;

import com.examination3.address.presentation.address.AddressResponse;
import com.examination3.address.presentation.address.AddressResponses;
import com.examination3.address.usecase.AddressDto;
import com.examination3.address.usecase.AddressGetAllUsecase;
import com.examination3.address.usecase.AddressGetByIdUsecase;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AddressController {
    private final AddressGetAllUsecase addressGetAllUsecase;
    private final AddressGetByIdUsecase addressGetByIdUsecase;

    /**
     * 全ての住所情報を取得します。
     *
     * @return 書籍のレスポンスデータのリスト
     */
    @GetMapping("/v1/addresses")
    @ResponseStatus(HttpStatus.OK)
    public AddressResponses getAddresses() {
        List<AddressResponse> addressResponses = addressGetAllUsecase.execute().stream()
            .map(this::convertDtoToResponse).toList();

        return new AddressResponses(addressResponses);
    }

    @GetMapping("v1/addresses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable String id) {
        Optional<AddressDto> addressDto = addressGetByIdUsecase.execute(id);
        return addressDto.map(a -> ResponseEntity.ok(convertDtoToResponse(a)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private AddressResponse convertDtoToResponse(AddressDto dto) {
        return new AddressResponse(String.valueOf(dto.id()), dto.zipCode(), dto.prefecture(),
            dto.city(), dto.streetAddress());
    }
}
