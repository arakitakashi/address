package com.examination3.address.usecase;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.AddressRepository;
import com.examination3.address.domain.exception.AddressNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 指定されたIDの住所情報の取得を行うユースケースクラス。 リポジトリを利用して、操作を行います。
 */
@Service
@RequiredArgsConstructor
public class AddressGetByIdUsecase {
    private final AddressRepository addressRepository;

    /**
     * 指定されたIDの住所情報を取得します。 住所情報が存在しない場合は空のOptionalを返します。
     *
     * @param id IDの値。
     * @return 指定されたIDの住所情報を含むDto。
     */
    public AddressDto execute(String id) {
        return addressRepository.findById(id).map(Address::toDto)
            .orElseThrow(() -> new AddressNotFoundException(id));
    }
}
