package com.examination3.address.usecase;

import com.examination3.address.domain.address.AddressRepository;
import com.examination3.address.domain.exception.AddressNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 住所情報の削除を行うユースケースクラス。 リポジトリを利用して、操作を行います。
 */
@Service
@RequiredArgsConstructor
public class AddressDeleteUsecase {
    private final AddressRepository addressRepository;

    /**
     * 指定されたIDの住所情報を削除します。
     *
     * @param id IDの値。
     */
    public void execute(String id) {
        boolean isSuccess = addressRepository.delete(id);
        if (!isSuccess) {
            throw new AddressNotFoundException(id);
        }
    }
}
