package bookshop.system.services;

import bookshop.system.models.entity.Category;
import bookshop.system.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private static final String CATEGORY_FILE_PATH = "src/main/resources/file/categories.txt";

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0){
            return;
        }

        Files.readAllLines(Path.of(CATEGORY_FILE_PATH))
                .stream()
                .filter(row -> !row.isEmpty())
                .forEach(categoryName -> {

                    Category category = new Category(categoryName);
                    categoryRepository.save(category);
                });
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();

        Random random = new Random();
        int randomSize = random.nextInt(1, 9);

        for (int i = 0; i < 4; i++) {
            Long randomId = random.nextLong(1,9);
            Category category = categoryRepository.findById(randomId).orElse(null);
            categories.add(category);
        }

        return categories;
    }
}
