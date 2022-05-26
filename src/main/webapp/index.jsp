<%@ page import="com.adminitions.entities.users.User" %>
<%@ page import="com.adminitions.entities.users.Role" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admitions</title>
    <jsp:include page="WEB-INF/bootstrapHead.html"/>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="#" class="navbar-brand">Admitions</a>
        </div>

        <%
            User user;
            try{
                user = (User) request.getSession().getAttribute("User");
            }
            catch (Exception exception){
                user = null;
            }
        %>
        <div>
            <ul class="nav navbar-nav navbar-right">
                <%if (user == null || user.getRole() == Role.UNKNOWN) {%>
                <li class="active">
                    <a href="Login">Login</a>
                </li>
                <li class="active">
                    <a href="#">Sing up</a>
                </li>
                <%
                } else if (user.getRole() == Role.APPLICANT) {
                %>
                <li class="active">
                    <a href="#">Applicant</a>
                </li>
                <li class="active">
                    <a href="#">Logout</a>
                </li>
                <%
                } else if (user.getRole() == Role.ADMIN) {
                %>
                <li class="active">
                    <a href="#">Admin</a>
                </li>
                <li class="active">
                    <a href="#">Logout</a>
                </li>
                <%}%>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>