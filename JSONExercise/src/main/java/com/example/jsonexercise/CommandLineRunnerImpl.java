package com.example.jsonexercise;

import com.example.jsonexercise.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;

    public CommandLineRunnerImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
    }
}
