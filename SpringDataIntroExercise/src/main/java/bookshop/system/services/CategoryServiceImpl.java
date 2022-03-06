package bookshop.system.services;

import bookshop.system.models.entity.Category;
import bookshop.system.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() > 0){
            return;
        }
        String CATEGORY_FILE_PATH = "src/main/resources/file/categories.txt";
        Files.readAllLines(Path.of(CATEGORY_FILE_PATH))
                .stream()
                .filter(row -> !row.isEmpty())
                .forEach(categoryName -> {

                    Category category = new Category(categoryName);
                    categoryRepository.save(category);
                });
    }
}
