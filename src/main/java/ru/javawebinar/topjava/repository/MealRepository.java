package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    List<Meal> getAll();

    Meal getById(Long id);

    void addMeal(Meal meal);

    void updateMeal(Meal meal);

    void delete(Long id);
}
