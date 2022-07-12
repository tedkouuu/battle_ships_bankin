package com.example.ships.repositories;

import com.example.ships.models.Category;
import com.example.ships.models.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByName(CategoryEnum type);
}
