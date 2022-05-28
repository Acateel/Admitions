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
    <title><fmt:message key="send_request"/></title>
    <jsp:include page="../bootstrapHead.html"/>
</head>
<body>
<jsp:include page="../Navbar.jsp"/>
<form action="SendRequest" method="post">
    <div class="container">
        <h1><fmt:message key="send_request"/></h1>
        <p>
            <fmt:message key="send_request_message"/>
        </p>
        <hr>
        <em class="text-danger">
            <fmt:message key="required"/>
        </em>
        <br/>
        <label for="main_subject">
            <fmt:message key="main_subject"/><span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="<fmt:message key="enter"/> <fmt:message key="score"/>" name="main_subject" id="main_subject" required>
        <p class="text-danger">${requestScope.MainSubjectError}</p>

        <label for="second_subject">
            <fmt:message key="second_subject"/><span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="<fmt:message key="enter"/> <fmt:message key="score"/>" name="second_subject" id="second_subject" required>
        <p class="text-danger">${requestScope.SecondSubjectError}</p>

        <label for="sub_subject">
            <fmt:message key="sub_subject"/><span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="<fmt:message key="enter"/> <fmt:message key="score"/>" name="sub_subject" id="sub_subject" required>
        <p class="text-danger">${requestScope.SubSubjectError}</p>

        <label for="average_attestation_score">
            <fmt:message key="average_attestation_score"/><span class="text-danger">*</span></label>
        <input type="text" class="form-control" placeholder="<fmt:message key="enter"/> <fmt:message key="score"/>" name="average_attestation_score" id="average_attestation_score" required>
        <p class="text-danger">${requestScope.AverageScoreError}</p>

        <hr>
        <button type="submit" class="button"><fmt:message key="send_request"/></button>
    </div>
</form>
</body>
</html>
