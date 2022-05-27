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
<h1>All Faculties</h1>
<table>
    <c:forEach var="faculty" items="${requestScope.faculties}">
        <ul>
            <li>Id: <c:out value="${faculty.id}"/></li>
            <li>Name: <c:out value="${faculty.name}"/></li>
            <li>Budget seats: <c:out value="${faculty.budgetSeats}"/></li>
            <li>Total seats: <c:out value="${faculty.totalSeats}"/></li>
        </ul>
    </c:forEach>
</table>
</body>
</html>
