package com.lpatros.ecommerce_api.repository;

import com.lpatros.ecommerce_api.entity.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long idToIgnore);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long idToIgnore);

    Optional<UserDetails> findUserByEmail(String username);
}
