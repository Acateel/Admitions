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
    <h1><fmt:message key="all_faculties"/></h1>
    <form action="Faculty" method="post">
        <label for="order">
            <fmt:message key="order"/>
        </label>
        <select name="order" id="order">
            <option value="byId"><fmt:message key="by_id"/></option>
            <option value="byName"><fmt:message key="by_name"/></option>
            <option value="byNameRevers"><fmt:message key="by_name_reverse"/></option>
            <option value="byBudget"><fmt:message key="by_budget_seats"/></option>
            <option value="byTotal"><fmt:message key="by_total_seats"/></option>
        </select>
        <button>
            <fmt:message key="order_button"/>
        </button>
    </form>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="id"/></th>
            <th scope="col"><fmt:message key="name"/></th>
            <th scope="col"><fmt:message key="budget_seats"/></th>
            <th scope="col"><fmt:message key="total_sears"/></th>
            <th scope="col"><fmt:message key="request"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="faculty" items="${requestScope.faculties}">
            <tr>
                <th scope="row"><c:out value="${faculty.id}"/></th>
                <td><c:out value="${faculty.name}"/></td>
                <td><c:out value="${faculty.budgetSeats}"/></td>
                <td><c:out value="${faculty.totalSeats}"/></td>
                <td><a href="Request?faculty_id=<c:out value="${faculty.id}"/>"><fmt:message key="request_href"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
