<%--
  Created by IntelliJ IDEA.
  User: Taras
  Date: 28.05.2022
  Time: 22:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locales.content"/>
<html>
<head>
    <title><fmt:message key="faculties_menu"/></title>
    <jsp:include page="../bootstrapHead.html"/>
</head>
<body>
<jsp:include page="../Navbar.jsp"/>
<div class="container">
    <h1><fmt:message key="all_faculties"/></h1>
    <form action="FacultyModeration?type=add" method="post">
        <input type="submit" class="btn btn-light" value="<fmt:message key="add_faculty"/>"/>
    </form>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="id"/></th>
            <th scope="col"><fmt:message key="name"/></th>
            <th scope="col"><fmt:message key="budget_seats"/></th>
            <th scope="col"><fmt:message key="total_sears"/></th>
            <th scope="col"><fmt:message key="request"/></th>
            <th scope="col"><fmt:message key="delete"/></th>
            <th scope="col"><fmt:message key="change"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="faculty" items="${requestScope.faculties}">
            <tr>
                <th scope="row"><c:out value="${faculty.id}"/></th>
                <td><c:out value="${faculty.name}"/></td>
                <td><c:out value="${faculty.budgetSeats}"/></td>
                <td><c:out value="${faculty.totalSeats}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/RequestModeration?faculty_id=<c:out value="${faculty.id}"/>">
                        <input type="button" class="btn text-primary" value=" <fmt:message key="request_href"/>"/>
                    </a>
                </td>
                <td>
                    <form action="FacultyModeration?faculty_id=<c:out value="${faculty.id}"/>&type=delete" method="post">
                        <input type="submit" class="btn text-primary" value="<fmt:message key="delete"/>"/>
                    </form>
                </td>
                <td>
                    <form action="FacultyModeration?faculty_id=<c:out value="${faculty.id}"/>&type=change" method="post">
                        <input type="submit" class="btn text-primary" value="<fmt:message key="change"/>"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>

