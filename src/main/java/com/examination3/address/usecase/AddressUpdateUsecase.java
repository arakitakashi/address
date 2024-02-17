package com.examination3.address.usecase;

import static org.apache.commons.lang3.StringUtils.isBlank;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.AddressRepository;
import com.examination3.address.domain.address.City;
import com.examination3.address.domain.address.Prefecture;
import com.examination3.address.domain.address.StreetAddress;
import com.examination3.address.domain.address.ZipCode;
import com.examination3.address.domain.exception.AddressNotFoundException;
import com.examination3.address.presentation.address.AddressRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 指定されたIDの住所情報の更新を行うユースケースクラス。 リポジトリを利用して、操作を行います。
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AddressUpdateUsecase {
    private final AddressRepository addressRepository;

    /**
     * 既存の住所情報を更新します。
     *
     * @param id             IDの値。
     * @param addressRequest 更新する住所情報のリクエスト。
     */
    @SuppressWarnings("unused") // orElseThrow の戻り値に関する警告を抑制
    public void execute(String id, AddressRequest addressRequest) {
        Address existingAddress = addressRepository.findById(id)
            .orElseThrow(() -> new AddressNotFoundException(id));

        Address address = requestToAddress(existingAddress, addressRequest);
        Optional<Address> resultAddress = addressRepository.update(address);
        if (resultAddress.isEmpty()) {
            throw new AddressNotFoundException(String.valueOf(address.id().value()));
        }
    }

    private Address requestToAddress(Address existingAddress, AddressRequest addressRequest) {
        ZipCode updateZipCode =
            !isBlank(addressRequest.zipCode()) ? new ZipCode(addressRequest.zipCode())
                : existingAddress.zipCode();

        Prefecture updatePrefecture =
            !isBlank(addressRequest.prefecture()) ? new Prefecture(addressRequest.prefecture())
                : existingAddress.prefecture();

        City updateCity =
            !isBlank(addressRequest.city()) ? new City(addressRequest.city())
                : existingAddress.city();

        StreetAddress updateStreetAddress =
            !isBlank(addressRequest.streetAddress()) ? new StreetAddress(
                addressRequest.streetAddress()) : existingAddress.streetAddress();

        return new Address(
            existingAddress.id(),
            updateZipCode,
            updatePrefecture,
            updateCity,
            updateStreetAddress
        );
    }
}
