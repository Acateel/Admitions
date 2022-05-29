<%--
  Created by IntelliJ IDEA.
  User: Taras
  Date: 28.05.2022
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locales.content"/>
<html>
<head>
    <title><fmt:message key="admin_menu"/></title>
    <jsp:include page="../bootstrapHead.html"/>
</head>
<body>
<jsp:include page="../Navbar.jsp"/>
<div class="container">
    <a href="FacultyModeration">
        <button type="button" class="btn btn-light btn-lg btn-block"><fmt:message key="faculties_menu"/></button>
    </a>
    <br/>
    <a href="ApplicantModeration">
        <button type="button" class="btn btn-light btn-lg btn-block"><fmt:message key="applicants"/></button>
    </a>
    <br/>
    <a href="Finalizer">
        <button type="button" class="btn btn-warning btn-lg btn-block"><fmt:message key="finalise_information"/></button>
    </a>
</div>
</body>
</html>
