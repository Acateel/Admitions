<%--
  Created by IntelliJ IDEA.
  User: Taras
  Date: 01.06.2022
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locales.content"/>
<html>
<head>
    <title><fmt:message key="user_info"/></title>
    <jsp:include page="../bootstrapHead.html"/>
</head>
<body>
<jsp:include page="../Navbar.jsp"/>
<div class="container">
    <h2><fmt:message key="user_info"/></h2>
    <br/>
    <table class="table table-striped">
        <tbody>
            <tr>
                <td style="width: 15%"><fmt:message key="id"/>:</td>
                <td>${requestScope.applicant.id}</td>
            </tr>
            <tr>
                <td style="width: 15%"><fmt:message key="lastname"/>:</td>
                <td>${requestScope.applicant.lastName}</td>
            </tr>
            <tr>
                <td style="width: 15%"><fmt:message key="firstname"/>:</td>
                <td>${requestScope.applicant.name}</td>
            </tr>
            <tr>
                <td style="width: 15%"><fmt:message key="surname"/>:</td>
                <td>${requestScope.applicant.surname}</td>
            </tr>
            <tr>
                <td style="width: 15%"><fmt:message key="email"/>:</td>
                <td>${requestScope.applicant.email}</td>
            </tr>
            <tr>
                <td style="width: 15%"><fmt:message key="city"/>:</td>
                <td>${requestScope.applicant.city}</td>
            </tr>
            <tr>
                <td style="width: 15%"><fmt:message key="region"/>:</td>
                <td>${requestScope.applicant.region}</td>
            </tr>
            <tr>
                <td style="width: 15%"><fmt:message key="education"/>:</td>
                <td>${requestScope.applicant.nameEducationalInstitution}</td>
            </tr>
        </tbody>
    </table>
    <form enctype="multipart/form-data"
          action="ApplicantInfo"
          method="post">
        <label for="file"><fmt:message key="attestation_scan"/></label>
        <br/>
        <input type="file" name="file" id="file"/>
        <input class="btn text-primary" type="submit" name="Upload" value="<fmt:message key="upload"/>"/>
    </form>
</div>
</body>
</html>
