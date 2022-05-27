<%--
  Created by IntelliJ IDEA.
  User: Taras
  Date: 21.05.2022
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locales.content"/>
<html>
<head>
    <title><fmt:message key="faculties"/></title>
    <jsp:include page="../bootstrapHead.html"/>
</head>
<body>
<jsp:include page="../Navbar.jsp"/>
<div class="container">
    <h1>All Faculties</h1>
    <form action="Faculty" method="post">
        <label for="order">Order</label>
        <select name="order" id="order">
            <option value="byId">by Id</option>
            <option value="byName">by Name (A-Z)</option>
            <option value="byNameRevers">by Name (Z-A)</option>
            <option value="byBudget">by Budget seats</option>
            <option value="byTotal">by Total seats</option>
        </select>
        <button>
            Order
        </button>
    </form>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">id</th>
            <th scope="col">Name</th>
            <th scope="col">Budget seats</th>
            <th scope="col">Total seats</th>
            <th scope="col">Requests</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="faculty" items="${requestScope.faculties}">
            <tr>
                <th scope="row"><c:out value="${faculty.id}"/></th>
                <td><c:out value="${faculty.name}"/></td>
                <td><c:out value="${faculty.budgetSeats}"/></td>
                <td><c:out value="${faculty.totalSeats}"/></td>
                <td><a href="#">See requests</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
