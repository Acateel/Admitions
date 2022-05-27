<%@ page import="com.adminitions.entities.users.User" %>
<%@ page import="com.adminitions.entities.users.Role" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="uk_UA"/>
<fmt:setBundle basename="locales.content"/>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="${pageContext.request.contextPath}/index.jsp" class="navbar-brand">
                <fmt:message key="logo"/>
            </a>
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
                    <a href="Login">
                        <fmt:message key="login"/>
                    </a>
                </li>
                <li class="active">
                    <a href="Registration">
                        <fmt:message key="register"/>
                    </a>
                </li>
                <%
                } else if (user.getRole() == Role.APPLICANT) {
                %>
                <li class="active">
                    <a href="#"><%= request.getSession().getAttribute("Name")%></a>
                </li>
                <li class="active">
                    <a href="Logout">
                        <fmt:message key="logout"/>
                    </a>
                </li>
                <%
                } else if (user.getRole() == Role.ADMIN) {
                %>
                <li class="active">
                    <a href="#">Admin</a>
                </li>
                <li class="active">
                    <a href="Logout">
                        <fmt:message key="logout"/>
                    </a>
                </li>
                <%}%>
            </ul>
        </div>
    </div>
</nav>
