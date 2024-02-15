package com.examination3.address.usecase;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.AddressRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 住所情報の全件取得を行うユースケースクラス。 リポジトリを利用して、操作を行います。
 */
@Service
@RequiredArgsConstructor
public class AddressGetAllUsecase {
    private final AddressRepository addressRepository;

    /**
     * 全ての住所情報を取得します。
     *
     * @return 住所情報のリスト。
     */
    public List<AddressDto> execute() {
        return addressRepository.findAll().stream().map(Address::toDto).toList();
    }
}
