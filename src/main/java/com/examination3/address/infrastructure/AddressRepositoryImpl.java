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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
            log.warn(DATABASE_ACCESS_ERROR_MESSAGE, e);
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
            log.warn("Data access error.", e);
            throw e;
        }
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
