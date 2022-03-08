package com.example.advquerying;

import com.example.advquerying.entities.Size;
import com.example.advquerying.repository.IngredientRepository;
import com.example.advquerying.repository.ShampooRepository;
import com.example.advquerying.service.IngredientService;
import com.example.advquerying.service.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

    @Override
    public void run(String... args) {
        //1.	Select Shampoos by Size
//        this.shampooService.selectBySize(Size.MEDIUM)
//                .forEach(System.out::println);

        //2.	Select Shampoos by Size or Label
//        this.shampooService.selectBySizeOrLabelId(Size.MEDIUM, 10)
//                .forEach(System.out::println);

        //3.	Select Shampoos by Price
//        this.shampooService.selectMoreExpensiveThan(BigDecimal.valueOf(5))
//                .forEach(System.out::println);

        //4.	Select Ingredients by Name
//        this.ingredientService.selectIngredientsByName("M")
//                .forEach(ingredient -> System.out.println(ingredient.getName()));
        //5.	Select Ingredients by Names
        this.ingredientService.selectIngredientsByName("M")
//                .forEach(ingredient -> System.out.println(ingredient.getName()));

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
