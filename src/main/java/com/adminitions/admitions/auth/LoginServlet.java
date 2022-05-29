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
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet(name = "LoginServlet", value = "/Login")
public class LoginServlet extends HttpServlet {

    protected transient UserDao userDao;
    protected transient ApplicantDao applicantDao;

    @Override
    public void init() throws ServletException {
        userDao = (UserDao) getServletContext().getAttribute("UserDao");
        applicantDao = (ApplicantDao) getServletContext().getAttribute("ApplicantDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        ResourceBundle bundle = getResourceBundle(request);

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
                request.setAttribute("Error", bundle.getString("login_error"));
                doGet(request, response);
            }
        } catch (DaoException e) {
            // add log and response
            throw new RuntimeException(e);
        }
    }

    private ResourceBundle getResourceBundle(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("lang");
        if(locale.length() > 0){
            String[] lamgs = locale.split("_");
            return ResourceBundle.getBundle("locales.content", new Locale(lamgs[0], lamgs[1]));
        }
        return ResourceBundle.getBundle("locales.content", new Locale(locale));
    }

    private void addFullNameInSession(HttpSession session, User user) throws DaoException {
        Applicant applicant = applicantDao.findEntityById(user.getApplicantId());
        String fullName = applicant.getLastName() + " " + applicant.getName();
        session.setAttribute("Name", fullName);
    }
}
