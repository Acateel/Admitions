<%@ page import="com.adminitions.entities.users.User" %>
<%@ page import="com.adminitions.entities.users.Role" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locales.content"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="logo" /></title>
    <jsp:include page="WEB-INF/bootstrapHead.html"/>
</head>
<body>
<jsp:include page="WEB-INF/Navbar.jsp"/>
<h2 class="text-info">${requestScope.SendRequestStatus}</h2>
</body>
</html>