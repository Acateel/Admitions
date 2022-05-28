<%--
  Created by IntelliJ IDEA.
  User: Taras
  Date: 28.05.2022
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locales.content"/>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../bootstrapHead.html"/>
</head>
<body>
<jsp:include page="../Navbar.jsp"/>
<h1>Send request form</h1>
</body>
</html>
