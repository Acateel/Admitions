<%--
  Created by IntelliJ IDEA.
  User: Taras
  Date: 26.05.2022
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locales.content"/>
<html>
<head>
    <title><fmt:message key="register"/></title>
    <jsp:include page="../bootstrapHead.html"/>
</head>
<body>
<jsp:include page="../Navbar.jsp"/>

<form action="Registration" method="post">
    <div class="container">
        <h1><fmt:message key="register"/></h1>
        <p>
            <fmt:message key="register_message"/>
        </p>
        <hr>
        <em class="text-danger">
            <fmt:message key="required"/>
        </em>
        <br/>
        <label for="email">
            <fmt:message key="email"/><span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="<fmt:message key="enter"/> <fmt:message key="email"/>" name="email" id="email" required>
        <p class="text-danger">${requestScope.EmailError}</p>

        <label for="psw"><fmt:message key="password"/><span class="text-danger">*</span></label>
        <input type="password" class="form-control" placeholder="<fmt:message key="enter"/> <fmt:message key="password"/>" name="psw" id="psw" required>
        <p class="text-danger">${requestScope.PasswordError}</p>

        <label for="psw-repeat"><fmt:message key="password_repeat"/><span class="text-danger">*</span></label>
        <input type="password" class="form-control" placeholder="<fmt:message key="password_repeat"/>" name="psw-repeat" id="psw-repeat" required>
        <p class="text-danger">${requestScope.PasswordRepeatError}</p>

        <label for="lastname"><fmt:message key="lastname"/><span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="<fmt:message key="enter"/> <fmt:message key="lastname"/>" name="lastname" id="lastname" required>
        <p class="text-danger">${requestScope.LastNameError}</p>

        <label for="firstname"><fmt:message key="firstname"/><span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="<fmt:message key="enter"/> <fmt:message key="firstname"/>" name="firstname" id="firstname" required>
        <p class="text-danger">${requestScope.FirstNameError}</p>

        <label for="surname"><fmt:message key="surname"/><span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="<fmt:message key="enter"/> <fmt:message key="surname"/>" name="surname" id="surname" required>
        <p class="text-danger">${requestScope.SurnameError}</p>

        <label for="city"><fmt:message key="city"/><span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="<fmt:message key="enter"/> <fmt:message key="city"/>" name="city" id="city" required>
        <p class="text-danger">${requestScope.CityError}</p>

        <label for="region"><fmt:message key="region"/><span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="<fmt:message key="enter"/> <fmt:message key="region"/>" name="region" id="region" required>
        <p class="text-danger">${requestScope.RegionError}</p>

        <label for="education"><fmt:message key="education"/><span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="<fmt:message key="enter"/> <fmt:message key="education"/>" name="education" id="education" required>
        <p class="text-danger">${requestScope.InstitutionError}</p>
        <hr>
        <button type="submit" class="button"><fmt:message key="register"/></button>
    </div>

    <div class="container">
        <p><fmt:message key="login_message"/> <a href="Login"><fmt:message key="login"/></a>.</p>
    </div>
</form>
</body>
</html>
