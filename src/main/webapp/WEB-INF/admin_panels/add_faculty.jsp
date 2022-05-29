<%--
  Created by IntelliJ IDEA.
  User: Taras
  Date: 29.05.2022
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locales.content"/>
<html>
<head>
    <title><fmt:message key="add_faculty"/></title>
    <jsp:include page="../bootstrapHead.html"/>
</head>
<body>
<jsp:include page="../Navbar.jsp"/>
<form action="AddFaculty" method="post">
    <div class="container">
        <h1><fmt:message key="add_faculty"/></h1>
        <hr>
        <em class="text-danger">
            <fmt:message key="required"/>
        </em>
        <br/>
        <label for="faculty_name">
            <fmt:message key="name"/><span class="text-danger">*</span></label>
        <input type="text" class="form-control"
               placeholder="<fmt:message key="enter"/> <fmt:message key="name"/>"
               name="faculty_name"
               id="faculty_name" required>

        <label for="budget_seats">
            <fmt:message key="budget_seats"/><span class="text-danger">*</span></label>
        <input type="text" class="form-control"
               placeholder="<fmt:message key="enter"/> <fmt:message key="budget_seats"/>"
               name="budget_seats" id="budget_seats" required>

        <label for="total_seats">
            <fmt:message key="total_sears"/><span class="text-danger">*</span></label>
        <input type="text" class="form-control"
               placeholder="<fmt:message key="enter"/> <fmt:message key="total_sears"/>"
               name="total_seats" id="total_seats" required>

        <hr>
        <button type="submit" class="button"><fmt:message key="add_faculty"/></button>
    </div>
</form>
</body>
</html>
