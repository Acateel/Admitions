<%@ page import="com.adminitions.entities.Faculty" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Taras
  Date: 21.05.2022
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Faculty> faculties = (List<Faculty>)request.getAttribute("faculties"); %>
<html>
<head>
    <title>Faculties</title>
</head>
<body>
<h1>All Faculties</h1>
<table>
    <%
        for(Faculty faculty : faculties){%>
    <tr>
        <td><%= faculty.getId()%></td>
        <td><%= faculty.getName() %></td>
        <td><%= faculty.getBudgetSeats() %></td>
        <td><%= faculty.getTotalSeats() %></td>
    </tr>
    <%}%>
</table>
</body>
</html>
