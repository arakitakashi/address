package com.examination3.address.infrastructure;

import com.examination3.address.domain.address.Address;
import com.examination3.address.domain.address.AddressRepository;
import com.examination3.address.domain.address.City;
import com.examination3.address.domain.address.Id;
import com.examination3.address.domain.address.Prefecture;
import com.examination3.address.domain.address.StreetAddress;
import com.examination3.address.domain.address.ZipCode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AddressRepositoryImpl implements AddressRepository {
    private static final String DATABASE_ACCESS_ERROR_MESSAGE = "Database Access Error";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Address> findAll() {
        String query = "SELECT id, zip_code, prefecture, city, street_address FROM addresses";

        try {
            List<AddressRecord> addressRecord = jdbcTemplate.query(query,
                new DataClassRowMapper<>(AddressRecord.class));
            return addressRecord.stream().map(this::mapToAddress).toList();
        } catch (DataAccessException e) {
            log.error(DATABASE_ACCESS_ERROR_MESSAGE, e);
            throw e;
        }
    }

    @Override
    public Optional<Address> findById(String id) {
        String query = "SELECT id, zip_code, prefecture, city, street_address FROM addresses WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", Integer.parseInt(id));

        try {
            AddressRecord addressRecord = jdbcTemplate.queryForObject(query, params,
                new DataClassRowMapper<>(AddressRecord.class));
            return Optional.ofNullable(mapToAddress(addressRecord));

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (DataAccessException e) {
            log.error(DATABASE_ACCESS_ERROR_MESSAGE, e);
            throw e;
        }
    }

    public Address register(Address address) {
        String query = "INSERT INTO addresses (id, zip_code, prefecture, city, street_address) VALUES (:id, :zip_code, :prefecture, :city, :street_address)";

        Map<String, Object> params = createRegisterParams(address);

        try {
            jdbcTemplate.update(query, params);
            return address;
        } catch (DataAccessException e) {
            log.error(DATABASE_ACCESS_ERROR_MESSAGE, e);
            throw e;
        }
    }

    private Map<String, Object> createRegisterParams(Address address) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", address.id().value());
        result.put("zip_code", address.zipCode().value());
        result.put("prefecture", address.prefecture().value());
        result.put("city", address.city().value());
        result.put("street_address", address.streetAddress().value());
        return result;
    }

    public int nextAddressId() {
        String sql = "SELECT NEXTVAL('ADDRESS_ID_SEQ')";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new HashMap<>(), Integer.class))
            .orElseThrow(() -> new IllegalStateException("Failed to fetch next address ID from sequence."));
    }

    @Override
    @Transactional
    public Optional<Address> update(Address address) {
        String query = """
            UPDATE addresses
            SET zip_code = :zip_code, prefecture = :prefecture, city = :city, street_address = :street_address
            WHERE id = :id
            """;

        Map<String, Object> params = createUpdateParams(address);

        try {
            jdbcTemplate.update(query, params);
            //TODO updateの結果でエラーハンドリングするか検討
            return Optional.of(address);
        } catch (DataAccessException e) {
            log.error(DATABASE_ACCESS_ERROR_MESSAGE, e);
            throw e;
        }
    }

    private Map<String, Object> createUpdateParams(Address address) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", address.id().value());
        result.put("zip_code", address.zipCode().value());
        result.put("prefecture", address.prefecture().value());
        result.put("city", address.city().value());
        result.put("street_address", address.streetAddress().value());
        return result;
    }

    private Address mapToAddress(AddressRecord addressRecord) {
        return new Address(
            new Id(addressRecord.id()),
            new ZipCode(addressRecord.zip_code()),
            new Prefecture(addressRecord.prefecture()),
            new City(addressRecord.city()),
            new StreetAddress(addressRecord.street_address())
        );
    }
}
