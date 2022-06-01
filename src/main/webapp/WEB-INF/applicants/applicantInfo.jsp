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
    <ul>
        <li><fmt:message key="id"/> ${requestScope.applicant.id}</li>
        <li><fmt:message key="lastname"/> ${requestScope.applicant.lastName}</li>
        <li><fmt:message key="firstname"/> ${requestScope.applicant.name}</li>
        <li><fmt:message key="surname"/> ${requestScope.applicant.surname}</li>
        <li><fmt:message key="email"/> ${requestScope.applicant.email}</li>
        <li><fmt:message key="city"/> ${requestScope.applicant.city}</li>
        <li><fmt:message key="region"/> ${requestScope.applicant.region}</li>
        <li><fmt:message key="education"/> ${requestScope.applicant.nameEducationalInstitution}</li>
        <li><fmt:message key="block_status"/> ${requestScope.applicant.block}</li>
    </ul>
    <form enctype="multipart/form-data"
          action="ApplicantInfo"
          method="post">
        <fmt:message key="attestation_scan"/> <input type="FILE" name="file"/>
        <input type="submit" name="Upload" value="<fmt:message key="upload"/>" />
    </form>
</div>
</body>
</html>
