package com.adminitions.admitions.auth;

import com.adminitions.data_access.ApplicantDao;
import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.UserDao;
import com.adminitions.entities.Applicant;
import com.adminitions.entities.users.Role;
import com.adminitions.entities.users.User;
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
                User user = userDao.findUser(login, password);
                session.setAttribute("User", user);
                if (user.getRole() == Role.APPLICANT){
                    addFullNameInSession(session, user);
                }
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

    private void addFullNameInSession(HttpSession session, User user) throws DaoException {
        ApplicantDao applicantDao = (ApplicantDao) getServletContext().getAttribute("ApplicantDao");
        Applicant applicant = applicantDao.findEntityById(user.getApplicantId());
        String fullName = applicant.getLastName() + " " + applicant.getName();
        session.setAttribute("Name", fullName);
    }
}
