package com.lpatros.ecommerce_api.repository;

import com.lpatros.ecommerce_api.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long idToIgnore);

    boolean existsByPhoneNumber_CountryCodeAndPhoneNumber_AreaCodeAndPhoneNumber_Number(String countryCode, String areaCode, String number);

    boolean existsByPhoneNumber_CountryCodeAndPhoneNumber_AreaCodeAndPhoneNumber_NumberAndIdNot(String countryCode, String areaCode, String number, Long id);
}
