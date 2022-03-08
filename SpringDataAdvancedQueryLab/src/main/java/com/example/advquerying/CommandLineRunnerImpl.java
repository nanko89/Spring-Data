package com.example.advquerying;

import com.example.advquerying.entities.Size;
import com.example.advquerying.repository.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final ShampooRepository shampooRepository;

    @Autowired
    public CommandLineRunnerImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public void run(String... args) throws Exception {


//        this.shampooRepository
//                .findByBrand("Cotton Fresh")
//                .forEach(s-> System.out.println(s.getId()));
//
//        this.shampooRepository
//                .findByBrandAndAndSize("Cotton Fresh", Size.SMALL)
//                .forEach(s-> System.out.println(s.getId()));

        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Size size = Size.valueOf(name);

        this.shampooRepository
                .findBySizeOrderById(size)
                .forEach(System.out::println);

        ;



    }
}
