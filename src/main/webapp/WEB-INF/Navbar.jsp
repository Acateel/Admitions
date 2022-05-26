<%@ page import="com.adminitions.entities.users.User" %>
<%@ page import="com.adminitions.entities.users.Role" %>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="../index.jsp" class="navbar-brand">Admitions</a>
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
                    <a href="#"><%= request.getSession().getAttribute("Name")%></a>
                </li>
                <li class="active">
                    <a href="Logout">Logout</a>
                </li>
                <%
                } else if (user.getRole() == Role.ADMIN) {
                %>
                <li class="active">
                    <a href="#">Admin</a>
                </li>
                <li class="active">
                    <a href="Logout">Logout</a>
                </li>
                <%}%>
            </ul>
        </div>
    </div>
</nav>
