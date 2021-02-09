package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealRepositoryMemory implements MealRepository {
    private Map<Long, Meal> mealMap = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    {
//        MealsUtil.meals.forEach(this::save);
        for (Meal meal : MealsUtil.meals) {
            meal.setId(counter.incrementAndGet());
            mealMap.put(meal.getId(), meal);
        }
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public Meal getById(Long id) {
        return mealMap.get(id);
    }

    @Override
    public void addMeal(Meal meal) {
        meal.setId(counter.incrementAndGet());
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        mealMap.remove(meal.getId());
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public void delete(Long id) {
        mealMap.remove(id);
    }
}
