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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MockData(CategoryRepository categoryRepository, ProductRepository productRepository, UserRepository userRepository, OrderRepository orderRepository, PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
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

        Product tech1 = new Product(null, "Samsung Galaxy S25 5G", "Smartphone com tela de 6.8 polegadas, câmera tripla de 108MP e bateria de longa duração.", 50, BigDecimal.valueOf(5999.99), "https://www.oficinadanet.com.br/media/obj_item/2047/381/samsung-galaxy-s25.jpg", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.getFirst());
        Product tech2 = new Product(null, "iPhone 17", "Câmera frontal Center Stage. Ceramic Shield de última geração. Tela ProMotion de 6,3 polegadas. Chip A19.", 30, BigDecimal.valueOf(3499.99), "https://m.media-amazon.com/images/I/51nj2ICJyeL._AC_SX679_.jpg", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.getFirst());
        Product tech3 = new Product(null, "MacBook Pro 16\"", "Processador M4 Max com 12 núcleos, 36GB de memória unificada, tela Liquid Retina XDR de 3456x2234.", 20, BigDecimal.valueOf(15999.99), "https://store.storeimages.cdn-apple.com/1/as-images.apple.com/is/mac-macbook-pro-finish-select-202601-16inch-silver_AV1?wid=5120&hei=3280&fmt=webp&qlt=90&.v=aXlkdGF0T0RUUVdDckNLaUc0OEE0MEhGUTRkVVZndC9KWVVLOUdiOXdHbWoyV1pjNVkyRnhZMXY4Y0JEMUlnV3kycG5rSmFSYlFlU3JVaTlXMmliMGtvbDF6d2tlR0VmV2RIWCtmOCtid1ZzaDZmRlFpSFhTc3dmMkpMNTNVQnFqV1VZSktRdEF2U3UzcG5CUzBlRWV3&traceId=1", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.getFirst());
        Product tech4 = new Product(null, "Dell XPS 13", "Laptop ultraportátil com tela OLED 3.5K, processador Intel Core i7, 16GB RAM e SSD de 512GB.", 25, BigDecimal.valueOf(8499.99), "https://i.dell.com/is/image/DellContent/content/dam/ss2/product-images/dell-client-products/notebooks/xps-notebooks/9345/media-gallery/touch/silver/xps-13-9345-laptop-silver-copilot-pc-mg.png?fmt=png-alpha&pscan=auto&scl=1&hei=402&wid=612&qlt=100,1&resMode=sharp2&size=612,402&chrss=full", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.getFirst());
        Product tech5 = new Product(null, "iPad Pro 12.9\"", "Tablet com tela Liquid Retina XDR, processador M4, câmera ultra grande angular e suporte a Apple Pencil Pro.", 35, BigDecimal.valueOf(9999.99), "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcRpaNzEoFrN1vTzhbvAn1aPcipuRKuWpImroKw1i6DM7bbRswp7OY88PN8hNNP1JNPsM4pnfqYTVhEmT01SmZi0pRAwiKMTG_e9SeiSAnI-xRV7L92P4Weg", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.getFirst());
        Product tech6 = new Product(null, "AirPods Pro 2", "Fones de ouvido com cancelamento de ruído adaptativo, audio espacial e até 6 horas de bateria.", 100, BigDecimal.valueOf(1799.99), "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcTM3oyV61s_KRSF-X7YFQHP4S2ZuubnZCQ5PZzFgUL-VXFKQfKF9VH4i8fXayVP4bjl_N9o2lQdY0ZkHM1nTyrf9EuLwxeb5g", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.getFirst());
        Product tech7 = new Product(null, "Samsung Galaxy Watch 6", "Smartwatch com tela AMOLED, monitoramento de saúde avançado, GPS e até 3 dias de bateria.", 40, BigDecimal.valueOf(2499.99), "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcSs4_eGC2aTPwRCxY-KvOMpPY_dsMqPT6aMni3uVuLXaTxfWLkTzKXGqP6avnsZ1Xk7zh8KmAxlxfmfq8XByRiaQmBdCbX4g6BMYWjWdUq9ORRof1xdTjGolw", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.getFirst());
        Product tech8 = new Product(null, "Sony WH-1000XM5", "Headphone sem fio com cancelamento de ruído liderança de indústria e até 8 horas de bateria com cancelamento ativo.", 45, BigDecimal.valueOf(2999.99), "https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcS0d43Ka7Da_GkduNp-FJ0poB43bKYy4QOwPiuR7XZ_KX-qdZfIAlAhIkvTc_wKNyjc1NnJswA8PVO4KfK6FEOQdY3KffKtvkMaQsBAszNtITQD-uw617psLQ", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.getFirst());
        Product tech9 = new Product(null, "Google Pixel 9", "Smartphone com IA integrada, câmera com zoom 8x, tela OLED e até 72 horas de bateria com bateria estendida.", 55, BigDecimal.valueOf(4999.99), "https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcQElMe9lcwGU65c2SpqZ4BYjOq9ewSg41wB44QzykpQ8XG3_ahmc3-eoMkbqe4qxcr5KNB1931bWjGxnI8pK6m5HJv9DwY5gm8bEpOR0IjdiesS3nhR_TC-", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.getFirst());
        Product tech10 = new Product(null, "Asus ROG Ally", "Console portátil com processador AMD Ryzen Z1, tela OLED de 7 polegadas e suporte para jogos de PC.", 28, BigDecimal.valueOf(4499.99), "https://br.store.asus.com/media/catalog/product/p/r/principal_01-_rc73ya_.png", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.getFirst());

        Product sport1 = new Product(null, "Bola de Futebol Nike Premier", "Bola oficial FIFA com painel termotécnico para maior controle e precisão.", 200, BigDecimal.valueOf(299.99), "https://www.nike.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(1));
        Product sport2 = new Product(null, "Chuteira Adidas Predator", "Chuteira de futebol com tecnologia Control Skin para máximo toque na bola.", 80, BigDecimal.valueOf(799.99), "https://www.adidas.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(1));
        Product sport3 = new Product(null, "Bicicleta Mountain Bike Caloi Elite", "Bicicleta aro 29 com quadro de alumínio e suspensão dianteira.", 30, BigDecimal.valueOf(2999.99), "https://www.caloi.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(1));
        Product sport4 = new Product(null, "Tênis de Corrida Asics Gel-Nimbus", "Tênis com tecnologia Gel para amortecimento superior e conforto em longas distâncias.", 120, BigDecimal.valueOf(799.99), "https://www.asics.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(1));
        Product sport5 = new Product(null, "Bola de Basquete Spalding NBA", "Bola oficial da NBA com couro sintético de alta qualidade.", 150, BigDecimal.valueOf(249.99), "https://www.spalding.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(1));
        Product sport6 = new Product(null, "Raquete de Tênis Wilson Blade", "Raquete profissional com cordas já incluídas para máxima performance.", 45, BigDecimal.valueOf(1299.99), "https://www.wilson.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(1));
        Product sport7 = new Product(null, "Skate Profissional Creature", "Skate completo com lixa emborrachada e rolamentos ABEC-7.", 90, BigDecimal.valueOf(499.99), "https://www.creaturesskateboards.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(1));
        Product sport8 = new Product(null, "Mochila de Trilha North Face Recon", "Mochila com capacidade de 30 litros, estrutura de suporte ergonômico e múltiplos compartimentos.", 60, BigDecimal.valueOf(699.99), "https://www.thenorthface.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(1));
        Product sport9 = new Product(null, "Bola Suíça para Pilates 65cm", "Bola para exercícios de pilates e fitness com material antiderrapante.", 180, BigDecimal.valueOf(149.99), "https://www.decathlon.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(1));
        Product sport10 = new Product(null, "Kit Halteres Ajustáveis 20kg", "Par de halteres com pesos ajustáveis de 2kg a 20kg com estojo.", 75, BigDecimal.valueOf(899.99), "https://www.decathlon.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(1));

        Product tool1 = new Product(null, "Furadeira Bosch GSR 18V-EC", "Furadeira profissional sem fio com bateria de 18V, carregador rápido e duas baterias.", 35, BigDecimal.valueOf(1299.99), "https://www.bosch.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(2));
        Product tool2 = new Product(null, "Jogo de Chaves Phillips e Fenda 30 peças", "Kit completo com chaves de diferentes tamanhos em caixa organizadora.", 200, BigDecimal.valueOf(199.99), "https://www.tramontina.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(2));
        Product tool3 = new Product(null, "Chave Inglesa 40cm Adjustável", "Chave inglesa de aço carbono com ajuste micrométrico precisão.", 150, BigDecimal.valueOf(129.99), "https://www.tramontina.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(2));
        Product tool4 = new Product(null, "Marreta 2kg Black Decker", "Martelo com cabo de fibra de vidro resistente a impactos.", 90, BigDecimal.valueOf(89.99), "https://www.blackanddecker.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(2));
        Product tool5 = new Product(null, "Nível Laser Bosch", "Nível laser com alcance de até 30 metros e bateria incluída.", 40, BigDecimal.valueOf(799.99), "https://www.bosch.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(2));
        Product tool6 = new Product(null, "Caixa de Ferramentas Metálica 50cm", "Caixa de ferramentas com alça e compartimentos organizadores.", 120, BigDecimal.valueOf(349.99), "https://www.stanley.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(2));
        Product tool7 = new Product(null, "Jogo de Brocas 13 peças", "Conjunto de brocas de aço rápido para perfuração em metal e madeira.", 180, BigDecimal.valueOf(79.99), "https://www.bosch.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(2));
        Product tool8 = new Product(null, "Chave de Fenda Telescópica 6 em 1", "Chave de fenda com haste telescópica e 6 pontas intercambiáveis.", 160, BigDecimal.valueOf(89.99), "https://www.stanley.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(2));
        Product tool9 = new Product(null, "Serra Elétrica Makita", "Serra circular com potência de 1500W e disco de corte de 185mm.", 25, BigDecimal.valueOf(899.99), "https://www.makita.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(2));
        Product tool10 = new Product(null, "Parafusadeira Elétrica 3.6V", "Parafusadeira de impacto com bateria de lítio e carregador rápido.", 70, BigDecimal.valueOf(299.99), "https://www.blackanddecker.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(2));

        Product furn1 = new Product(null, "Sofá 3 Lugares Cinza", "Sofá estofado em tecido cinza com almofadas removíveis e estrutura de madeira maciça.", 15, BigDecimal.valueOf(2499.99), "https://www.mobly.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(3));
        Product furn2 = new Product(null, "Mesa de Jantar de Vidro 6 Lugares", "Mesa com tampo de vidro temperado e base em metal cromado, acompanha 6 cadeiras.", 8, BigDecimal.valueOf(1999.99), "https://www.mobly.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(3));
        Product furn3 = new Product(null, "Cama Box Queen 1.58x1.98m", "Cama box com colchão espuma HR de 28cm de altura e base estofada.", 25, BigDecimal.valueOf(1599.99), "https://www.mobly.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(3));
        Product furn4 = new Product(null, "Guarda-roupa 6 Portas Branco", "Guarda-roupa em MDF com interior organizado, espelhos e prateleiras ajustáveis.", 12, BigDecimal.valueOf(1299.99), "https://www.mobly.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(3));
        Product furn5 = new Product(null, "Rack para TV 1.5m Preto", "Rack suspenso com prateleiras de vidro temperado e acabamento em preto.", 40, BigDecimal.valueOf(699.99), "https://www.mobly.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(3));
        Product furn6 = new Product(null, "Estante Libreria 5 Prateleiras", "Estante de madeira com 5 prateleiras para livros e decoração.", 55, BigDecimal.valueOf(499.99), "https://www.mobly.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(3));
        Product furn7 = new Product(null, "Poltrona Reclinável Marrom", "Poltrona em couro reclinável com mecanismo elétrico e aquecimento.", 18, BigDecimal.valueOf(1899.99), "https://www.mobly.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(3));
        Product furn8 = new Product(null, "Escrivaninha Gamer 120cm", "Mesa de computador com suporte para monitor, teclado e mouse, em preto.", 50, BigDecimal.valueOf(799.99), "https://www.mobly.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(3));
        Product furn9 = new Product(null, "Banco de Madeira 45cm", "Banco em madeira de demolição estilo rústico e moderno.", 65, BigDecimal.valueOf(399.99), "https://www.mobly.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(3));
        Product furn10 = new Product(null, "Painel TV Suspenso Branco", "Painel para TV com nichos e prateleiras em MDF branco brilhante.", 20, BigDecimal.valueOf(599.99), "https://www.mobly.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(3));

        Product appl1 = new Product(null, "Geladeira Brastemp 600L", "Geladeira frost-free com inverter, congelador espaçoso e prateleiras de vidro.", 16, BigDecimal.valueOf(4999.99), "https://www.brastemp.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(4));
        Product appl2 = new Product(null, "Fogão 5 Bocas Consul Branco", "Fogão com 5 queimadores, forno com luz interna e visor de vidro.", 22, BigDecimal.valueOf(1299.99), "https://www.consul.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(4));
        Product appl3 = new Product(null, "Lavadora 12kg Electrolux", "Lavadora de roupas automática com ciclos de lavagem inteligentes e inverter.", 18, BigDecimal.valueOf(2199.99), "https://www.electrolux.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(4));
        Product appl4 = new Product(null, "Micro-ondas Electrolux 28L", "Micro-ondas com 8 níveis de potência, prato giratório e termômetro sensor.", 40, BigDecimal.valueOf(599.99), "https://www.electrolux.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(4));
        Product appl5 = new Product(null, "Lava-louças Brastemp 12 Programas", "Lava-louças com 12 programas de lavagem e capacidade para 14 cubertos.", 14, BigDecimal.valueOf(2899.99), "https://www.brastemp.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(4));
        Product appl6 = new Product(null, "Secadora Electrolux 10kg", "Secadora com ciclos de secagem variáveis e sensor inteligente de umidade.", 12, BigDecimal.valueOf(1899.99), "https://www.electrolux.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(4));
        Product appl7 = new Product(null, "Fritadeira Elétrica Sem Óleo", "Fritadeira de ar com capacidade de 4L, timer e temperatura ajustável até 200°C.", 85, BigDecimal.valueOf(299.99), "https://www.mondial.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(4));
        Product appl8 = new Product(null, "Ventilador de Teto Premium", "Ventilador de teto com 3 velocidades, controle remoto e luz LED integrada.", 55, BigDecimal.valueOf(799.99), "https://www.ventisol.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(4));
        Product appl9 = new Product(null, "Ar Condicionado 12000 BTU LG", "Ar condicionado split com tecnologia inverter, Wi-Fi e filtro auto-limpante.", 11, BigDecimal.valueOf(2499.99), "https://www.lg.com/br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(4));
        Product appl10 = new Product(null, "Liquidificador Oster Pro 1200W", "Liquidificador com motor de 1200W, 8 velocidades e jarra de vidro de 1,5L.", 70, BigDecimal.valueOf(399.99), "https://www.oster.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(4));

        Product toy1 = new Product(null, "Lego Classic 500 peças", "Conjunto Lego com 500 peças de cores variadas para montar várias criações.", 110, BigDecimal.valueOf(299.99), "https://www.lego.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(5));
        Product toy2 = new Product(null, "Boneco Action Figure Super-Homem", "Boneco articulado de 15cm do Super-Homem com detalhes reais.", 250, BigDecimal.valueOf(79.99), "https://www.mattel.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(5));
        Product toy3 = new Product(null, "Carrinho Hot Wheels Set com 20 unidades", "Kit com 20 carrinhos em escala 1:64 de diferentes cores e modelos.", 180, BigDecimal.valueOf(129.99), "https://www.mattel.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(5));
        Product toy4 = new Product(null, "Bicicleta Infantil 20 polegadas", "Bicicleta para crianças com rodinha de treino, freio e design colorido.", 25, BigDecimal.valueOf(599.99), "https://www.caloi.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(5));
        Product toy5 = new Product(null, "Patinete 3 Rodas com LED", "Patinete com 3 rodas, LED nas rodas, guidão ajustável e freio traseiro.", 95, BigDecimal.valueOf(249.99), "https://www.oxelo.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(5));
        Product toy6 = new Product(null, "Jogo Tabuleiro Banco Imobiliário", "Clássico jogo de tabuleiro para a família com 2 a 8 jogadores.", 200, BigDecimal.valueOf(89.99), "https://www.hasbro.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(5));
        Product toy7 = new Product(null, "Bola de Pilha Eletrônica", "Bola que pisca e faz sons quando jogada de um lado para o outro.", 140, BigDecimal.valueOf(99.99), "https://www.multikids.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(5));
        Product toy8 = new Product(null, "Quebra-Cabeça 500 peças Paisagem", "Quebra-cabeça com 500 peças de uma bela paisagem natural.", 160, BigDecimal.valueOf(49.99), "https://www.grow.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(5));
        Product toy9 = new Product(null, "Drone Infantil com Câmera", "Mini drone com câmera HD, controle remoto e bateria recarregável.", 40, BigDecimal.valueOf(399.99), "https://www.dji.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(5));
        Product toy10 = new Product(null, "Barraca Infantil Castelo", "Barraca de brinquedo em formato de castelo com cores vibrantes.", 30, BigDecimal.valueOf(199.99), "https://www.multikids.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(5));

        Product cloth1 = new Product(null, "Camiseta Básica Branca 100% Algodão", "Camiseta básica de algodão premium com gola redonda e acabamento reforçado.", 300, BigDecimal.valueOf(59.99), "https://www.hering.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(6));
        Product cloth2 = new Product(null, "Calça Jeans Azul Escuro", "Calça jeans com ajuste regular, desbotado moderno e bolsos funcionais.", 180, BigDecimal.valueOf(149.99), "https://www.hering.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(6));
        Product cloth3 = new Product(null, "Jaqueta de Couro Marrom", "Jaqueta em couro genuíno com forro de viscose e zíperes de qualidade.", 35, BigDecimal.valueOf(999.99), "https://www.dudalina.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(6));
        Product cloth4 = new Product(null, "Vestido Floral Midi", "Vestido com estampa floral, manga curta e tecido confortável.", 120, BigDecimal.valueOf(249.99), "https://www.arezzo.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(6));
        Product cloth5 = new Product(null, "Blusa de Seda Vermelha", "Blusa em seda pura com botões de pérola e gola clássica.", 90, BigDecimal.valueOf(349.99), "https://www.dudalina.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(6));
        Product cloth6 = new Product(null, "Calça Legging Cinza", "Legging em poliamida com cintura alta, controle de barriga e tecido opaco.", 200, BigDecimal.valueOf(129.99), "https://www.hering.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(6));
        Product cloth7 = new Product(null, "Suéter Gola Alta Marrom", "Suéter em 100% algodão premium com gola alta e mangas compridas.", 150, BigDecimal.valueOf(189.99), "https://www.hering.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(6));
        Product cloth8 = new Product(null, "Short Jeans Azul Claro", "Short jeans com barra desfiada e ajuste perfeito para o verão.", 220, BigDecimal.valueOf(99.99), "https://www.hering.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(6));
        Product cloth9 = new Product(null, "Camiseta Estampada Preta", "Camiseta com estampa moderna em padrão abstrato e algodão macio.", 280, BigDecimal.valueOf(79.99), "https://www.hering.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(6));
        Product cloth10 = new Product(null, "Jaqueta Jeans Azul", "Jaqueta jeans com bolsos funcionais e botões reforçados.", 110, BigDecimal.valueOf(199.99), "https://www.hering.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(6));

        Product shoe1 = new Product(null, "Tênis Esportivo Branco", "Tênis com tecnologia de amortecimento, sola de borracha resistente e design moderno.", 180, BigDecimal.valueOf(349.99), "https://www.nike.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(7));
        Product shoe2 = new Product(null, "Sapato Social Preto Couro", "Sapato social em couro legítimo com sola de couro e design clássico.", 90, BigDecimal.valueOf(449.99), "https://www.havaianas.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(7));
        Product shoe3 = new Product(null, "Sandália Birkenstock Arizona", "Sandália anatômica com tiras de couro e cortiça natural ultra confortável.", 140, BigDecimal.valueOf(299.99), "https://www.birkenstock.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(7));
        Product shoe4 = new Product(null, "Bota de Couro Marrom", "Bota em couro com zíper lateral e sola de borracha antiderrapante.", 60, BigDecimal.valueOf(599.99), "https://www.arezzo.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(7));
        Product shoe5 = new Product(null, "Havaianas Top Premium", "Sandália tradicional em borracha de qualidade com tira ergonômica.", 250, BigDecimal.valueOf(79.99), "https://www.havaianas.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(7));
        Product shoe6 = new Product(null, "Tênis de Corrida Preto", "Tênis com malha respirável, palmilha ortopédica e sola com tecnologia de amortecimento.", 175, BigDecimal.valueOf(379.99), "https://www.asics.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(7));
        Product shoe7 = new Product(null, "Sapatilha Ballet Rosa", "Sapatilha profissional em satin com sola dividida e fita elástica.", 85, BigDecimal.valueOf(199.99), "https://www.katz.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(7));
        Product shoe8 = new Product(null, "Loafer Mocassim Azul", "Mocassim em camurça com aplicação de metal e conforto excepcional.", 110, BigDecimal.valueOf(399.99), "https://www.arezzo.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(7));
        Product shoe9 = new Product(null, "Tênis Casual Cinza", "Tênis em lona com design minimalist e sola de borracha branca.", 200, BigDecimal.valueOf(189.99), "https://www.vans.com/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(7));
        Product shoe10 = new Product(null, "Sapato Feminino Salto Médio Vermelho", "Sapato com salto médio de 5cm em couro verniz e design elegante.", 95, BigDecimal.valueOf(299.99), "https://www.arezzo.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(7));

        Product book1 = new Product(null, "O Senhor dos Anéis - Edição Especial", "Trilogia clássica de J.R.R. Tolkien em edição especial com capa dura e marcador.", 35, BigDecimal.valueOf(189.99), "https://www.companhiadasletras.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(8));
        Product book2 = new Product(null, "1984 - George Orwell", "Romance distópico essencial com reflexões sobre totalitarismo e controle social.", 120, BigDecimal.valueOf(49.99), "https://www.companhiadasletras.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(8));
        Product book3 = new Product(null, "Dom Casmurro - Machado de Assis", "Clássico da literatura brasileira com análise profunda de relacionamentos.", 200, BigDecimal.valueOf(34.99), "https://www.companhiadasletras.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(8));
        Product book4 = new Product(null, "O Poder do Hábito - Charles Duhigg", "Livro de não-ficção sobre como criar e manter bons hábitos.", 95, BigDecimal.valueOf(59.99), "https://www.rocco.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(8));
        Product book5 = new Product(null, "A Menina que Roubava Livros", "Romance histórico emocionante ambientado durante a Segunda Guerra Mundial.", 85, BigDecimal.valueOf(54.99), "https://www.rocco.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(8));
        Product book6 = new Product(null, "Sapiens - Yuval Noah Harari", "História da humanidade desde o surgimento até os dias atuais.", 60, BigDecimal.valueOf(79.99), "https://www.rocco.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(8));
        Product book7 = new Product(null, "O Pequeno Príncipe - Antoine de Saint-Exupéry", "Clássico infantil e adulto com ilustrações originais e reflexões profundas.", 150, BigDecimal.valueOf(44.99), "https://www.companhiadasletras.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(8));
        Product book8 = new Product(null, "Mindset - Carol S. Dweck", "Livro sobre mentalidade de crescimento e como alcançar sucesso.", 110, BigDecimal.valueOf(64.99), "https://www.rocco.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(8));
        Product book9 = new Product(null, "Harry Potter e a Pedra Filosofal", "Primeiro livro da série mágica de J.K. Rowling que conquistou o mundo.", 140, BigDecimal.valueOf(69.99), "https://www.rocco.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(8));
        Product book10 = new Product(null, "Crime e Castigo - Dostoiévski", "Obra-prima da literatura russa com profunda análise psicológica.", 70, BigDecimal.valueOf(74.99), "https://www.companhiadasletras.com.br/", LocalDateTime.now(), LocalDateTime.now(), Boolean.FALSE, categoryList.get(8));

        List<Product> productList = Arrays.asList(
                tech1, tech2, tech3, tech4, tech5, tech6, tech7, tech8, tech9, tech10,
                sport1, sport2, sport3, sport4, sport5, sport6, sport7, sport8, sport9, sport10,
                tool1, tool2, tool3, tool4, tool5, tool6, tool7, tool8, tool9, tool10,
                furn1, furn2, furn3, furn4, furn5, furn6, furn7, furn8, furn9, furn10,
                appl1, appl2, appl3, appl4, appl5, appl6, appl7, appl8, appl9, appl10,
                toy1, toy2, toy3, toy4, toy5, toy6, toy7, toy8, toy9, toy10,
                cloth1, cloth2, cloth3, cloth4, cloth5, cloth6, cloth7, cloth8, cloth9, cloth10,
                shoe1, shoe2, shoe3, shoe4, shoe5, shoe6, shoe7, shoe8, shoe9, shoe10,
                book1, book2, book3, book4, book5, book6, book7, book8, book9, book10
        );
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
                passwordEncoder.encode("12345678"),
                LocalDate.now(),
                "Rua ABC, 100, Apto 100, Bairro XYZ, São Paulo - SP, 12345-678",
                null,
                LocalDateTime.now(),
                Boolean.FALSE,
                "ADMIN"
        );

        User user2 = new User(
                null,
                "234567890-12",
                "Fernando",
                "+55 (11) 92345-6789",
                "fernando@mail.com",
                passwordEncoder.encode("12345678"),
                LocalDate.now(),
                "Rua DEF, 200, Apto 200, Bairro UVW, São Paulo - SP, 23456-789",
                null,
                LocalDateTime.now(),
                Boolean.FALSE,
                "USER"
        );

        List<User> userList = Arrays.asList(user1, user2);
        userRepository.saveAll(userList);

        return userList;
    }
}
