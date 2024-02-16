package com.examination3.address.infrastructure;

import static java.util.Objects.isNull;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link AddressRepository}のJDBCによる実装。 住所情報のデータベース操作を担います。
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class AddressRepositoryImpl implements AddressRepository {
    private static final String DATABASE_ACCESS_ERROR_MESSAGE = "Database Access Error";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Address> findById(String id) {
        String query =
            "SELECT id, zip_code, prefecture, city, street_address FROM addresses WHERE id = :id";

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

    private Address mapToAddress(AddressRecord addressRecord) {
        if (isNull(addressRecord)) {
            return null;
        }
        return new Address(
            new Id(addressRecord.id()),
            new ZipCode(addressRecord.zipCode()),
            new Prefecture(addressRecord.prefecture()),
            new City(addressRecord.city()),
            new StreetAddress(addressRecord.streetAddress())
        );
    }

    /**
     * {@inheritDoc}
     */
    public Address register(Address address) {
        String query = """
            INSERT INTO addresses (id, zip_code, prefecture, city, street_address) 
            VALUES (:id, :zipCode, :prefecture, :city, :streetAddress)
            """;

        Map<String, Object> params = createParams(address);

        try {
            jdbcTemplate.update(query, params);
            return address;
        } catch (DataAccessException e) {
            log.error(DATABASE_ACCESS_ERROR_MESSAGE, e);
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    public int nextAddressId() {
        String sql = "SELECT NEXTVAL('ADDRESS_ID_SEQ')";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new HashMap<>(), Integer.class))
            .orElseThrow(
                () -> new IllegalStateException("Failed to fetch next address ID from sequence."));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Optional<Address> update(Address address) {
        String query = """
            UPDATE addresses
            SET 
            zip_code = :zipCode, 
            prefecture = :prefecture, 
            city = :city, 
            street_address = :streetAddress
            WHERE id = :id
            """;

        Map<String, Object> params = createParams(address);

        try {
            int affectedRows = jdbcTemplate.update(query, params);
            if (affectedRows == 0) {
                log.warn(String.format("No Address found with ID: %d", address.id().value()));
                return Optional.empty();
            }
            return Optional.of(address);
        } catch (DataAccessException e) {
            log.error(DATABASE_ACCESS_ERROR_MESSAGE, e);
            throw e;
        }
    }

    private Map<String, Object> createParams(Address address) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", address.id().value());
        result.put("zipCode", address.zipCode().value());
        result.put("prefecture", address.prefecture().value());
        result.put("city", address.city().value());
        result.put("streetAddress", address.streetAddress().value());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public boolean delete(String id) {
        try {
            String query = "DELETE FROM addresses WHERE id = :id";

            Map<String, Object> params = createDeleteParams(id);

            int affectedRows = jdbcTemplate.update(query, params);

            return affectedRows > 0;
        } catch (DataAccessException e) {
            log.error(DATABASE_ACCESS_ERROR_MESSAGE, e);
            throw e;
        }
    }

    private Map<String, Object> createDeleteParams(String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", Integer.parseInt(id));
        return result;
    }
}
