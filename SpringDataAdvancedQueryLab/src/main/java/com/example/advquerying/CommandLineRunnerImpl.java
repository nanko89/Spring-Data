package com.example.advquerying;

import com.example.advquerying.entities.Size;
import com.example.advquerying.repository.IngredientRepository;
import com.example.advquerying.repository.ShampooRepository;
import com.example.advquerying.service.IngredientService;
import com.example.advquerying.service.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final ShampooRepository shampooRepository;
    private final ShampooService shampooService;
    private final IngredientRepository ingredientRepository;
    private final IngredientService ingredientService;

    @Autowired
    public CommandLineRunnerImpl(ShampooRepository shampooRepository,
                                 ShampooService shampooService,
                                 IngredientRepository ingredientRepository,
                                 IngredientService ingredientService) {
        this.shampooRepository = shampooRepository;
        this.shampooService = shampooService;
        this.ingredientRepository = ingredientRepository;
        this.ingredientService = ingredientService;
    }

    @Transactional
    public void run(String... args) {
        //1.	Select Shampoos by Size
        this.shampooService.selectBySize(Size.MEDIUM)
                .forEach(System.out::println);

        //2.	Select Shampoos by Size or Label
        this.shampooService.selectBySizeOrLabelId(Size.MEDIUM, 10)
                .forEach(System.out::println);

        //3.	Select Shampoos by Price
        this.shampooService.selectMoreExpensiveThan(BigDecimal.valueOf(5))
                .forEach(System.out::println);

        //4.	Select Ingredients by Name
        this.ingredientService.selectIngredientsByName("M")
                .forEach(ingredient -> System.out.println(ingredient.getName()));

        //5.	Select Ingredients by Names
        this.ingredientService.selectIngredientsInNames(List.of("Lavender", "Herbs", "Apple"))
              .forEach(ingredient -> System.out.println(ingredient.getName()));

       // 6.	Count Shampoos by Price
        int shampoo = this.shampooService.countShampooByPrice(BigDecimal.valueOf(8.50));
        System.out.println(shampoo);

        //7.	Select Shampoos by Ingredients
        this.shampooRepository.findByIngredientsNames(Set.of("Berry","Mineral-Collagen"))
                .forEach(System.out::println);

        //8.	Select Shampoos by Ingredients Count
        this.shampooService.selectShampooByIngredientsCount(2)
                .forEach(System.out::println);

        //9.	Delete Ingredients by Name
        this.ingredientService.deleteByName("Nettle");

        //10.	Update Ingredients by Price
        this.ingredientService.increasePriceByPercentage(0.1);

        //11.	Update Ingredients by Names
        this.ingredientService.increasePriceByGivenNames(0.1, Set.of("Cherry", "Berry"));
    }

    private void demo(){

        Scanner scanner = new Scanner(System.in);

        String first = scanner.nextLine();
        String  second = scanner.nextLine();
        Set<String> names = Set.of(first, second);

        this.shampooRepository.findByIngredientsNames(names)
                .forEach(System.out::println);

        this.shampooRepository
                .findByBrand("Cotton Fresh")
                .forEach(s-> System.out.println(s.getId()));

        this.shampooRepository
                .findByBrandAndAndSize("Cotton Fresh", Size.SMALL)
                .forEach(s-> System.out.println(s.getId()));

        String name = scanner.nextLine();
        Size size = Size.valueOf(name);

        this.shampooRepository
                .findBySizeOrderById(size)
                .forEach(System.out::println);
    }
}
