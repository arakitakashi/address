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

    /**
     * 指定されたIDの住所情報を取得します。
     *
     * @param id IDの値。
     * @return 住所情報。
     */
    Optional<Address> findById(String id);

    /**
     * 指定された住所情報を登録します。
     *
     * @param address 住所オブジェクト。
     * @return 登録された住所情報。
     */
    Address register(Address address);

    /**
     * 指定された住所情報を更新します。
     *
     * @param address 住所オブジェクト。
     * @return 更新された住所情報。
     */
    Optional<Address> update(Address address);

    /**
     * 指定されたIDの住所情報を削除します。
     *
     * @param id IDの値。
     * @return 削除の成否を表すブーリアン。
     */
    boolean delete(String id);

    /**
     * データベースから付番される次のIDの値を返します。
     *
     * @return 付番された新しいIDの値。
     */
    int nextAddressId();
}
