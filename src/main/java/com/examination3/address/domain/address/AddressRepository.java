package com.examination3.address.domain.address;

import java.util.List;
import java.util.Optional;

/**
 * 住所情報に対するリポジトリのインターフェース。　住所情報の検索、登録、更新、削除の操作を定義します。
 */
public interface AddressRepository {

    /**
     * 全ての住所情報を取得します。
     *
     * @return 住所情報のリスト。
     */
    List<Address> findAll();

    Optional<Address> findById(String id);

    Address register(Address address);

    Optional<Address> update(Address address);

    boolean delete(String id);

    int nextAddressId();
}
