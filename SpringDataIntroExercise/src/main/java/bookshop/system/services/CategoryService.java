package bookshop.system.services;

import bookshop.system.models.entity.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {

    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();

}
