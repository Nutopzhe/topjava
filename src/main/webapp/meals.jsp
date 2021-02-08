<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<h3>
    <a href="meals?action=create">Add meal</a>
</h3>

<ul>
    <table border="1" cellpadding="6" cellspacing="2">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>

        <jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealTo>"/>
        <jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>

        <c:forEach var="mt" items="${meals}">
            <tr style="color: ${mt.excess ? 'red' : 'green'}">
                <td>
                    ${mt.dateTime.format(formatter)}
                </td>
                <td>${mt.description}</td>
                <td>${mt.calories}</td>
                <th>
                    <a href="meals?action=update&id=${mt.id}">Update</a>
                </th>
                <th>
                    <a href="meals?action=delete&id=${mt.id}">Delete</a>
                </th>
            </tr>
        </c:forEach>
    </table>
</ul>

<div>
    <button onclick="location.href='/topjava'">Back to main</button>
</div>

</body>
</html>
