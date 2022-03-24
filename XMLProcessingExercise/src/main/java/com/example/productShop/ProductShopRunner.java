package com.example.productShop;

import com.example.productShop.model.dto.category.CategoriesViewRootDTO;
import com.example.productShop.model.dto.category.CategorySeedRootDTO;
import com.example.productShop.model.dto.product.ProductSeedRootDTO;
import com.example.productShop.model.dto.product.ProductViewRootDTO;
import com.example.productShop.model.dto.user.UserCountViewRootDTO;
import com.example.productShop.model.dto.user.UserSeedRootDTO;
import com.example.productShop.model.dto.user.UserViewRootDTO;
import com.example.productShop.service.CategoryService;
import com.example.productShop.service.ProductService;
import com.example.productShop.service.UserService;
import com.example.productShop.util.XmlParse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

@Component
public class ProductShopRunner implements CommandLineRunner {

    public static final String PATH_OF_FILES = "src/main/resources/Files/product.store/";
    public static final String CATEGORY_PATH = "categories.xml";
    public static final String USER_PATH = "users.xml";
    public static final String PRODUCT_PATH = "products.xml";
    public static final String OUTPUT_PATH = "src/main/resources/Files/out/";
    public static final String PRODUCT_IN_RANGE = "product-in-range.xml";
    public static final String SOLD_PRODUCT = "users-sold-products.xml";
    public static final String CATEGORIES_BY_PRODUCTS = "categories-by-products.xml";
    public static final String USER_AND_PRODUCTS = "user-and-products.xml";

    private final XmlParse xmlParse;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final BufferedReader bufferedReader;

    @Autowired
    public ProductShopRunner(XmlParse xmlParse, CategoryService categoryService, UserService userService, ProductService productService) {
        this.xmlParse = xmlParse;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }


    @Override
    public void run(String... args) throws Exception {
        seedData();

        while (true) {
            System.out.println("Enter number of query for product shop:");

            int queryNumber = Integer.parseInt(bufferedReader.readLine());

            switch (queryNumber) {

                case 1 -> productInRange();
                case 2 -> soldProduct();
                case 3 -> categoryByProductsCount();
                case 4 -> usersAndProducts();
            }
        }
    }

    private void usersAndProducts() throws JAXBException {
        UserCountViewRootDTO userCountViewRootDTO = userService
                .findAllUserWithSoldProductOrderByCountLastAndFirstName();

        xmlParse.writeToFile(OUTPUT_PATH + USER_AND_PRODUCTS, userCountViewRootDTO);
    }

    private void categoryByProductsCount() throws JAXBException {
        CategoriesViewRootDTO categoriesViewRootDTO = categoryService
                .findAllCategoryOrderBYCountOfProducts();

        xmlParse
                .writeToFile(OUTPUT_PATH + CATEGORIES_BY_PRODUCTS,
                        categoriesViewRootDTO);
    }

    private void soldProduct() throws JAXBException {
        UserViewRootDTO userViewRootDTO = userService
                .findAllUserWithSoldProductOrderByLastAndFirstName();

        xmlParse
                .writeToFile(OUTPUT_PATH + SOLD_PRODUCT,
                        userViewRootDTO);
    }

    private void productInRange() throws JAXBException {

        ProductViewRootDTO productRoot = productService
                .findProductInRange500And1000WithNoBuyer();

        xmlParse
                .writeToFile(OUTPUT_PATH + PRODUCT_IN_RANGE,
                        productRoot);

    }

    private void seedData() throws JAXBException, FileNotFoundException {

        if (categoryService.getEntityCount() == 0) {

            CategorySeedRootDTO categorySeedRootDTO = xmlParse
                    .fromFile(PATH_OF_FILES + CATEGORY_PATH, CategorySeedRootDTO.class);

            categoryService.seedCategories(categorySeedRootDTO.getCategories());
        }

        if (userService.getEntityCount() == 0) {
            UserSeedRootDTO userSeedRootDTO = xmlParse
                    .fromFile(PATH_OF_FILES + USER_PATH, UserSeedRootDTO.class);

            userService.seedUser(userSeedRootDTO.getUser());
        }

        if (productService.getEntityCount() == 0) {
            ProductSeedRootDTO productSeedRootDTO = xmlParse
                    .fromFile(PATH_OF_FILES + PRODUCT_PATH, ProductSeedRootDTO.class);

            productService.seedProduct(productSeedRootDTO.getProducts());
        }
    }
}
