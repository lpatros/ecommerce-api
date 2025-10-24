package com.lpatros.ecommerce_api.repository;

import com.lpatros.ecommerce_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    List<User> findUsersByCpf(String cpf);

    List<User> findUsersByPhoneNumber(String phoneNumber);

    List<User> findUsersByEmail(String email);
}
