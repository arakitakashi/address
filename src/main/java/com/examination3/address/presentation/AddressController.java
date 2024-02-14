package com.examination3.address.presentation;

import com.examination3.address.presentation.address.AddressRequest;
import com.examination3.address.presentation.address.AddressResponse;
import com.examination3.address.presentation.address.AddressResponses;
import com.examination3.address.usecase.AddressDeleteUsecase;
import com.examination3.address.usecase.AddressDto;
import com.examination3.address.usecase.AddressGetAllUsecase;
import com.examination3.address.usecase.AddressGetByIdUsecase;
import com.examination3.address.usecase.AddressRegisterUsecase;
import com.examination3.address.usecase.AddressUpdateUsecase;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 住所情報に関する操作を提供するコントローラー。 このクラスは住所の検索、登録、更新、削除のためのエンドポイントを提供します。
 */
@RequiredArgsConstructor
@RestController
public class AddressController {
    private final AddressGetAllUsecase addressGetAllUsecase;
    private final AddressGetByIdUsecase addressGetByIdUsecase;
    private final AddressRegisterUsecase addressRegisterUsecase;
    private final AddressUpdateUsecase addressUpdateUsecase;
    private final AddressDeleteUsecase addressDeleteUsecase;

    /**
     * アプリケーションのルートエンドポイントに対するレスポンスを提供します。
     *
     * @return HTTPステータス200（OK）のレスポンスエンティティ
     */
    @GetMapping("/")
    public ResponseEntity<Void> getRoot() {
        return ResponseEntity.ok().build();
    }

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

    /**
     * 指定されたIDの住所情報を取得します。 存在しない場合は404 Not Foundを返します。
     *
     * @param id 　ID
     * @return 住所情報のレスポンスエンティティ
     */
    @GetMapping("v1/addresses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable String id) {
        AddressDto addressDto = addressGetByIdUsecase.execute(id);
        return ResponseEntity.ok(convertDtoToResponse(addressDto));
    }

    private AddressResponse convertDtoToResponse(AddressDto dto) {
        return new AddressResponse(String.valueOf(dto.id()), dto.zipCode(), dto.prefecture(),
            dto.city(), dto.streetAddress());
    }

    /**
     * 新しい住所情報を登録します。 登録に成功すると201 Createdとともに住所情報のURIを返します。
     *
     * @param addressRequest 新規住所情報のDTO
     * @return 作成された住所情報のURIを含むレスポンスエンティティ
     */
    @PostMapping("/v1/addresses")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createAddress(@RequestBody AddressRequest addressRequest) {
        AddressDto registeredAddress = addressRegisterUsecase.execute(addressRequest);
        URI location = getLocation(registeredAddress);
        return ResponseEntity.created(location).build();
    }

    private URI getLocation(AddressDto addressDto) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
            .buildAndExpand(addressDto.id()).toUri();
    }

    /**
     * 指定されたIDの住所情報を更新します。 更新に成功すると204 No Contentを返します。
     *
     * @param id             ID
     * @param addressRequest 更新する住所の情報
     * @Return レスポンスエンティティ。
     */
    @PatchMapping("/v1/addresses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updateAddress(
        @PathVariable String id,
        @RequestBody AddressRequest addressRequest
    ) {
        addressUpdateUsecase.execute(id, addressRequest);
        return ResponseEntity.noContent().build();
    }

    /**
     * 指定されたIDの住所情報を削除します。 削除に成功すると204 No Contentを返します。
     *
     * @param id ID
     * @return レスポンスエンティティ
     */
    @DeleteMapping("/v1/addresses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAddresses(@PathVariable String id) {
        addressDeleteUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
