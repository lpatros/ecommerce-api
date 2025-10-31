package com.lpatros.ecommerce_api.util;

import com.lpatros.ecommerce_api.entity.*;
import com.lpatros.ecommerce_api.repository.CategoryRepository;
import com.lpatros.ecommerce_api.repository.ProductRepository;
import com.lpatros.ecommerce_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("mock")
public class MockData implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public MockData(CategoryRepository categoryRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {

            productRepository.deleteAll();
            categoryRepository.deleteAll();
            userRepository.deleteAll();

            List<Category> categoryList = loadCategories();
            System.out.println(">>> Dados mock de Categoria carregados com sucesso!");

            loadProducts(categoryList);
            System.out.println(">>> Dados mock de Produto carregados com sucesso!");

            loadUsers();
            System.out.println(">>> Dados mock de Usuário carregados com sucesso!");

        } catch (Exception e) {
            System.out.println(">>> Erro ao carregar dados mock: " + e.getMessage());
        }
    }

    private List<Category> loadCategories() {

        Category category1 = new Category(
                null,
                "Tecnologia",
                Boolean.FALSE
        );

        Category category2 = new Category(
                null,
                "Esportes",
                Boolean.FALSE
        );

        Category category3 = new Category(
                null,
                "Ferramentas",
                Boolean.FALSE
        );

        Category category4 = new Category(
                null,
                "Móveis",
                Boolean.FALSE
        );

        Category category5 = new Category(
                null,
                "Eletrodomésticos",
                Boolean.FALSE
        );

        Category category6 = new Category(
                null,
                "Brinquedos",
                Boolean.FALSE
        );

        Category category7 = new Category(
                null,
                "Roupas",
                Boolean.FALSE
        );

        Category category8 = new Category(
                null,
                "Calçados",
                Boolean.FALSE
        );

        Category category9 = new Category(
                null,
                "Livros",
                Boolean.FALSE
        );

        List<Category> categoriesList = Arrays.asList(
                category1,
                category2,
                category3,
                category4,
                category5,
                category6,
                category7,
                category8,
                category9
        );

        categoryRepository.saveAll(categoriesList);

        return categoriesList;
    }

    private void loadProducts(List<Category> categoryList) {

        Product product1 = new Product(
                null,
                "Smartphone XYZ",
                "Smartphone com tela de 6.5 polegadas, 128GB de armazenamento e câmera tripla.",
                50,
                BigDecimal.valueOf(1999.99),
                "https://example.com/images/smartphone_xyz_front.jpg",
                LocalDateTime.now(),
                LocalDateTime.now(),
                Boolean.FALSE,
                categoryRepository.findById(categoryList.getFirst().getId()).orElse(null)
        );

        Product product2 = new Product(
                null,
                "TV ABC 55 Polegadas 4K",
                "Smart TV 55 polegadas com resolução 4K, HDR com AndroidTV.",
                30,
                BigDecimal.valueOf(3499.99),
                "https://example.com/images/tv_abc_front.jpg",
                LocalDateTime.now(),
                LocalDateTime.now(),
                Boolean.FALSE,
                categoryRepository.findById(categoryList.getFirst().getId()).orElse(null)
        );

        productRepository.saveAll(Arrays.asList(product1, product2));
    }

    private void loadUsers() {

        User user1 = new User(
                null,
                "123456789-01",
                "Leonardo",
                "+55 (11) 91234-5678",
                "leonardo@mail.com",
                "12345678",
                LocalDate.now(),
                "Rua ABC, 100, Apto 100, Bairro XYZ, São Paulo - SP, 12345-678",
                LocalDateTime.now(),
                Boolean.FALSE
                );

        User user2 = new User(
                null,
                "234567890-12",
                "Fernando",
                "+55 (11) 92345-6789",
                "fernando@mail.com",
                "12345678",
                LocalDate.now(),
                "Rua DEF, 200, Apto 200, Bairro UVW, São Paulo - SP, 23456-789",
                LocalDateTime.now(),
                Boolean.FALSE
        );

        userRepository.saveAll(Arrays.asList(user1, user2));
    }
}
