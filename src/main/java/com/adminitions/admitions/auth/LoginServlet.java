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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet(name = "LoginServlet", value = "/Login")
//Servlet responsible for logging in
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginServlet.class);
    protected transient UserDao userDao;
    protected transient ApplicantDao applicantDao;

    @Override
    // initialise Dao classes
    public void init() throws ServletException {
        logger.info("LoginServlet init");
        userDao = (UserDao) getServletContext().getAttribute("UserDao");
        applicantDao = (ApplicantDao) getServletContext().getAttribute("ApplicantDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("LoginServlet GET method");
        //redirects to the login page
        request.getRequestDispatcher("WEB-INF/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("LoginServlet POST method");
        // take login and password
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        ResourceBundle bundle = getResourceBundle(request);

        try {
            // if User with login and password exist
            if(userDao.isExist(login, password)){
                logger.trace("User exist in DB");
                // add user in session
                HttpSession session = request.getSession();
                User user = userDao.findUser(login, password);
                session.setAttribute("User", user);
                // if user is applicant then add full name into session
                if (user.getRole() == Role.APPLICANT){
                    logger.trace("User is applicant");
                    addFullNameInSession(session, user);
                }
                // back to main page
                response.sendRedirect("index.jsp");
            }
            else{
                logger.trace("User with login and password dont exist");
                // send error message and show login page
                request.setAttribute("Error", bundle.getString("login_error"));
                doGet(request, response);
            }
        } catch (DaoException e) {
            logger.error("Login DaoException");
        }
    }

    private ResourceBundle getResourceBundle(HttpServletRequest request) {
        // get language from session
        String locale = (String) request.getSession().getAttribute("lang");
        // if language dont default
        if(locale.length() > 0){
            String[] arguments = locale.split("_");
            return ResourceBundle.getBundle("locales.content", new Locale(arguments[0], arguments[1]));
        }
        // get default bundle
        return ResourceBundle.getBundle("locales.content", new Locale(locale));
    }

    private void addFullNameInSession(HttpSession session, User user) throws DaoException {
        Applicant applicant = applicantDao.findEntityById(user.getApplicantId());
        String fullName = applicant.getLastName() + " " + applicant.getName();
        session.setAttribute("Name", fullName);
    }
}
