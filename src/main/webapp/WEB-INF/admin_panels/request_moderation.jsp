<%--
  Created by IntelliJ IDEA.
  User: Taras
  Date: 29.05.2022
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locales.content"/>
<html>
<head>
    <title><fmt:message key="request"/></title>
    <jsp:include page="../bootstrapHead.html"/>
</head>
<body>
<jsp:include page="../Navbar.jsp"/>
<div class="container">
    <h1><fmt:message key="faculty"/></h1>
    <ul>
        <li>
            <fmt:message key="name"/>: ${requestScope.faculty.name}
        </li>
        <li>
            <fmt:message key="budget_seats"/>: ${requestScope.faculty.budgetSeats}
        </li>
        <li>
            <fmt:message key="total_sears"/>: ${requestScope.faculty.totalSeats}
        </li>
    </ul>
    <h1><fmt:message key="request"/></h1>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="id"/></th>
            <th scope="col"><fmt:message key="fullname"/></th>
            <th scope="col"><fmt:message key="main_subject"/></th>
            <th scope="col"><fmt:message key="second_subject"/></th>
            <th scope="col"><fmt:message key="sub_subject"/></th>
            <th scope="col"><fmt:message key="raging_score"/></th>
            <th scope="col"><fmt:message key="average_attestation_score"/></th>
            <th scope="col"><fmt:message key="status"/></th>
            <th scope="col"><fmt:message key="add_to_realize"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="request" items="${requestScope.requests}">
            <tr>
                <th scope="row"><c:out value="${request.id}"/></th>
                <td><c:out value="${request.applicantId}"/></td>
                <td><c:out value="${request.mainSubject}"/></td>
                <td><c:out value="${request.secondSubject}"/></td>
                <td><c:out value="${request.subSubject}"/></td>
                <td><c:out value="${request.ratingScore}"/></td>
                <td><c:out value="${request.averageAttestationScore}"/></td>
                <td><c:out value="${request.status}"/></td>
                <td>
                    <form action="RequestModeration?applicant_id=<c:out value="${request.applicantId}"/>" method="post">
                        <input type="submit" class="btn text-primary" value="<fmt:message key="add_to_realize"/>"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>

