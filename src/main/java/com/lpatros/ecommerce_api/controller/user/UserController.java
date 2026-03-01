package com.lpatros.ecommerce_api.controller.user;

import com.lpatros.ecommerce_api.configuration.Pagination;
import com.lpatros.ecommerce_api.dto.user.UserFilter;
import com.lpatros.ecommerce_api.dto.user.UserPatch;
import com.lpatros.ecommerce_api.dto.user.UserRequest;
import com.lpatros.ecommerce_api.dto.user.UserResponse;
import com.lpatros.ecommerce_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements User {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<Pagination<UserResponse>> findAll(UserFilter userFilter, Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(userFilter, pageable));
    }

    public ResponseEntity<UserResponse> findById(Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    public ResponseEntity<UserResponse> create(UserRequest userRequest) {
        return ResponseEntity.ok(userService.create(userRequest));
    }

    public ResponseEntity<UserResponse> update(Long id, UserRequest userRequest) {
        return ResponseEntity.ok(userService.update(id, userRequest));
    }

    public ResponseEntity<UserResponse> partialUpdate(Long id, UserPatch userPatch) {
        return ResponseEntity.ok(userService.partialUpdate(id, userPatch));
    }

    public ResponseEntity<Void> delete(Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
