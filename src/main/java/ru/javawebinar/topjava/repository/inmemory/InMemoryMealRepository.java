package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.*;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = getLogger(InMemoryMealRepository.class);

    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEAL_LIST_USER_1.forEach(meal -> save(1, meal));
        MealsUtil.MEAL_LIST_USER_2.forEach(meal -> save(2, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {} for {}", meal, userId);

        Map<Integer, Meal> currentRepository = new HashMap<>();

        if (repository.containsKey(userId)) {
            currentRepository = repository.get(userId);
        } else {
            repository.put(userId, currentRepository);
        }

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            currentRepository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return currentRepository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete {} for {}", id, userId);

        Map<Integer, Meal> currentRepository = repository.getOrDefault(userId, Collections.emptyMap());
        Meal meal = currentRepository.get(id);

        return meal != null && currentRepository.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get {} for {}", id, userId);

        Map<Integer, Meal> map = repository.getOrDefault(userId, Collections.emptyMap());

        return map.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");

        return getByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getAllFilteredWithDate(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAll filtered by date and time");

        return getByPredicate(userId, meal ->
                DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate));
    }

    private List<Meal> getByPredicate(Integer userId, Predicate<Meal> filter) {
        return userId == null ? Collections.emptyList() : repository.get(userId).values()
                .stream()
                .filter(filter)
                .sorted(Collections.reverseOrder(Comparator.comparing(Meal::getDate)))
                .collect(Collectors.toList());
    }
}

