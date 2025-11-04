package com.lpatros.ecommerce_api.util;

import com.lpatros.ecommerce_api.entity.*;
import com.lpatros.ecommerce_api.entity.order.Order;
import com.lpatros.ecommerce_api.entity.order.OrderItem;
import com.lpatros.ecommerce_api.entity.order.OrderStatus;
import com.lpatros.ecommerce_api.repository.CategoryRepository;
import com.lpatros.ecommerce_api.repository.OrderRepository;
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
import java.util.Collections;
import java.util.List;

@Component
@Profile("mock")
public class MockData implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public MockData(CategoryRepository categoryRepository, ProductRepository productRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) {
        try {

            orderRepository.deleteAll();
            productRepository.deleteAll();
            categoryRepository.deleteAll();
            userRepository.deleteAll();

            List<Category> categoryList = loadCategories();
            System.out.println(">>> Category mock data loaded successfully!");

            List<Product> productList = loadProducts(categoryList);
            System.out.println(">>> Product mock data loaded successfully!");

            List<User> userList = loadUsers();
            System.out.println(">>> User mock data loaded successfully!");

            List<Order> orderList = loadOrders(userList);
            System.out.println(">>> Order mock data loaded successfully!");

            loadOrderItems(orderList, productList);
            System.out.println(">>> Order Items mock data loaded successfully!");

        } catch (Exception e) {
            System.out.println(">>> Error loading mock data: " + e.getMessage());
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

    private List<Product> loadProducts(List<Category> categoryList) {

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
                categoryList.getFirst()
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
                categoryList.getFirst()
        );

        List<Product> productList = Arrays.asList(product1, product2);
        productRepository.saveAll(productList);

        return productList;
    }

    private void loadOrderItems(List<Order> orderList, List<Product> productList) {

        OrderItem orderItem1 = new OrderItem(
                null,
                1,
                BigDecimal.valueOf(1999.99),
                productList.get(0),
                orderList.get(0),
                Boolean.FALSE
        );

        OrderItem orderItem2 = new OrderItem(
                null,
                2,
                BigDecimal.valueOf(3499.99),
                productList.get(1),
                orderList.get(0),
                Boolean.FALSE
        );

        OrderItem orderItem3 = new OrderItem(
                null,
                3,
                BigDecimal.valueOf(1999.99),
                productList.get(0),
                orderList.get(1),
                Boolean.FALSE
        );

        BigDecimal totalOrder1 = orderItem1.getUnitPrice()
                .multiply(BigDecimal.valueOf(orderItem1.getQuantity()))
                .add(orderItem2.getUnitPrice().multiply(BigDecimal.valueOf(orderItem2.getQuantity())));

        BigDecimal totalOrder2 = orderItem3.getUnitPrice()
                .multiply(BigDecimal.valueOf(orderItem3.getQuantity()));

        orderList.get(0).setTotalPrice(totalOrder1);
        orderList.get(0).setOrderItems(Arrays.asList(orderItem1, orderItem2));

        orderList.get(1).setTotalPrice(totalOrder2);
        orderList.get(1).setOrderItems(Collections.singletonList(orderItem3));

        orderRepository.saveAll(orderList);
    }

    private List<Order> loadOrders(List<User> userList) {

        Order order1 = new Order(
                null,
                null,
                BigDecimal.ZERO,
                OrderStatus.PENDING,
                "TRACK123456",
                userList.get(0),
                LocalDateTime.now(),
                Boolean.FALSE
        );

        Order order2 = new Order(
                null,
                null,
                BigDecimal.ZERO,
                OrderStatus.PROCESSING,
                "TRACK789012",
                userList.get(1),
                LocalDateTime.now(),
                Boolean.FALSE
        );

        List<Order> orderList = Arrays.asList(order1, order2);
        orderRepository.saveAll(orderList);

        return orderList;
    }

    private List<User> loadUsers() {

        User user1 = new User(
                null,
                "123456789-01",
                "Leonardo",
                "+55 (11) 91234-5678",
                "leonardo@mail.com",
                "12345678",
                LocalDate.now(),
                "Rua ABC, 100, Apto 100, Bairro XYZ, São Paulo - SP, 12345-678",
                null,
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
                null,
                LocalDateTime.now(),
                Boolean.FALSE
        );

        List<User> userList = Arrays.asList(user1, user2);
        userRepository.saveAll(userList);

        return userList;
    }
}
