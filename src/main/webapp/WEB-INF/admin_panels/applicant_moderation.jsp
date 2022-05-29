<%--
  Created by IntelliJ IDEA.
  User: Taras
  Date: 29.05.2022
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locales.content"/>
<html>
<head>
    <title><fmt:message key="applicant_menu"/></title>
    <jsp:include page="../bootstrapHead.html"/>
</head>
<body>
<jsp:include page="../Navbar.jsp"/>
<div class="container">
    <h1><fmt:message key="all_applicants"/></h1>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="id"/></th>
            <th scope="col"><fmt:message key="lastname"/></th>
            <th scope="col"><fmt:message key="firstname"/></th>
            <th scope="col"><fmt:message key="surname"/></th>
            <th scope="col"><fmt:message key="status"/></th>
            <th scope="col"><fmt:message key="block"/></th>
            <th scope="col"><fmt:message key="remove_block"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="applicant" items="${requestScope.applicants}">
            <tr>
                <th scope="row"><c:out value="${applicant.id}"/></th>
                <td><c:out value="${applicant.lastName}"/></td>
                <td><c:out value="${applicant.name}"/></td>
                <td><c:out value="${applicant.surname}"/></td>
                <td><c:out value="${applicant.block}"/></td>
                <td>
                    <form action="ApplicantModeration?type=block&applicant_id=<c:out value="${applicant.id}"/>" method="post">
                        <input type="submit" class="btn text-primary" value="<fmt:message key="block"/>"/>
                    </form>
                </td>
                <td>
                    <form action="ApplicantModeration?type=deblock&applicant_id=<c:out value="${applicant.id}"/>" method="post">
                        <input type="submit" class="btn text-primary" value="<fmt:message key="remove_block"/>"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
