package com.example.ships.seeders;

import com.example.ships.models.Category;
import com.example.ships.models.CategoryEnum;
import com.example.ships.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategorySeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategorySeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.count() != 0) {
            return;
        }

        CategoryEnum[] allCategories = CategoryEnum.values();

        int counter = 1;

        for (CategoryEnum categoryName : allCategories) {

            Category category = new Category().setName(categoryName).setDescription("I am category number:" + counter);

            counter++;

            this.categoryRepository.save(category);
        }

    }
}
