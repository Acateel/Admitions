package com.adminitions.admitions.auth;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/Login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserDao userDao = (UserDao) getServletContext().getAttribute("UserDao");

        try {
            if(userDao.isExist(login, password)){
                HttpSession session = request.getSession();
                session.setAttribute("User", userDao.findUser(login, password));
                response.sendRedirect("index.jsp");
            }
            else{
                request.setAttribute("Error", "User with this login and password not found");
                doGet(request, response);
            }
        } catch (DaoException e) {
            // add log and response
            throw new RuntimeException(e);
        }
    }
}
