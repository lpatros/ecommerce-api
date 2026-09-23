package com.lpatros.ecommerce_api.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionsTest {

    @Test
    void notFoundException_messageAndFields() {
        NotFoundException ex = new NotFoundException("Product", "id");
        assertThat(ex.getMessage()).isEqualTo("Product not found with the id");
        assertThat(ex.getName()).isEqualTo("Product");
        assertThat(ex.getField()).isEqualTo("id");
    }

    @Test
    void notActiveException_message() {
        NotActiveException ex = new NotActiveException("User");
        assertThat(ex.getMessage()).isEqualTo("User has already been deleted");
        assertThat(ex.getName()).isEqualTo("User");
    }

    @Test
    void notUniqueException_messageAndFields() {
        NotUniqueException ex = new NotUniqueException("Product", "name");
        assertThat(ex.getMessage()).isEqualTo("Product already exists with the given name.");
        assertThat(ex.getName()).isEqualTo("Product");
        assertThat(ex.getField()).isEqualTo("name");
    }

    @Test
    void notNegativeException_messageAndFields() {
        NotNegativeException ex = new NotNegativeException("Product", "stock");
        assertThat(ex.getMessage()).isEqualTo("Product cannot be negative for the field stock.");
        assertThat(ex.getName()).isEqualTo("Product");
        assertThat(ex.getField()).isEqualTo("stock");
    }

    @Test
    void notMatchException_twoArgs_message() {
        NotMatchException ex = new NotMatchException("password", "confirm password");
        assertThat(ex.getMessage()).isEqualTo("password do not match confirm password");
    }

    @Test
    void notMatchException_defaultMessage() {
        NotMatchException ex = new NotMatchException();
        assertThat(ex.getMessage()).isEqualTo("Fields do not match");
    }

    @Test
    void duplicateItemsListException_message() {
        DuplicateItemsListException ex = new DuplicateItemsListException("Product");
        assertThat(ex.getMessage()).isEqualTo("Duplicate Product found in the list.");
        assertThat(ex.getItemType()).isEqualTo("Product");
    }

    @Test
    void restErrorMessage_gettersSetters() {
        RestErrorMessage msg = new RestErrorMessage();
        msg.setStatus(404);
        msg.setMessage("not found");
        assertThat(msg.getStatus()).isEqualTo(404);
        assertThat(msg.getMessage()).isEqualTo("not found");
    }
}
