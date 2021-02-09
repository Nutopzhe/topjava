package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryMemory;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static String INSERT_OR_EDIT = "/update.jsp";
    private static String LIST_USER = "/meals.jsp";

    private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new MealRepositoryMemory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("GET request");

        String forward = "";
        String action = (request.getParameter("action"));

        if (action == null) {
            action = "getall";
        }

        if (action.equalsIgnoreCase("delete")) {
            Long mealId = Long.parseLong(request.getParameter("id"));
            repository.delete(mealId);

            forward = LIST_USER;
            List<MealTo> meals = MealsUtil.getMealToList(repository.getAll());
            request.setAttribute("meals", meals);
            request.setAttribute("formatter", TimeUtil.getDateTimeFormatter());
        } else if (action.equalsIgnoreCase("update")) {
            long mealId = Long.parseLong(request.getParameter("id"));
            Meal meal = repository.getById(mealId);

            forward = INSERT_OR_EDIT;
            request.setAttribute("meal", meal);
            request.setAttribute("formatter", TimeUtil.getDateTimeFormatter());
        } else if (action.equalsIgnoreCase("create")) {
            Meal meal = new Meal(LocalDateTime.now(), null, 0);

            forward = INSERT_OR_EDIT;
            request.setAttribute("meal", meal);
            request.setAttribute("formatter", TimeUtil.getDateTimeFormatter());
        } else if (action.equalsIgnoreCase("getall")) {
            forward = LIST_USER;
            List<MealTo> meals = MealsUtil.getMealToList(repository.getAll());
            request.setAttribute("meals", meals);
            request.setAttribute("formatter", TimeUtil.getDateTimeFormatter());
        } else {
            forward = INSERT_OR_EDIT;
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("POST request");
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal newMeal = new Meal(dateTime, description, calories);

        if (id == null || id.isEmpty()) {
            repository.addMeal(newMeal);
        } else {
            newMeal.setId(Long.parseLong(id));
            repository.updateMeal(newMeal);
        }

        response.sendRedirect("meals");
    }
}
