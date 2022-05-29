package com.adminitions.admitions.auth;

import com.adminitions.data_access.ApplicantDao;
import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.UserDao;
import com.adminitions.entities.Applicant;
import com.adminitions.entities.users.Role;
import com.adminitions.entities.users.User;
import com.adminitions.validators.Validator;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet(name = "RegistrationServlet", value = "/Registration")
public class RegistrationServlet extends HttpServlet {
    protected transient ResourceBundle bundle;
    protected transient UserDao userDao;
    protected transient ApplicantDao applicantDao;

    @Override
    public void init() throws ServletException {
        userDao = (UserDao) getServletContext().getAttribute("UserDao");
        applicantDao = (ApplicantDao) getServletContext().getAttribute("ApplicantDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        Applicant applicant = new Applicant();
        bundle = getResourceBundle(request);
        setUserAndApplicant(request, response, user, applicant);
        addToDb(request, response, user, applicant);

        HttpSession session = request.getSession();
        session.setAttribute("User", user);
        String fullName = applicant.getLastName() + " " + applicant.getName();
        session.setAttribute("Name", fullName);
        response.sendRedirect("index.jsp");
    }


    private void addToDb(HttpServletRequest request, HttpServletResponse response, User user, Applicant applicant)
            throws ServletException, IOException {
        if (applicantExist(applicant, applicantDao)) {
            request.setAttribute("EmailError", bundle.getString("applicant_exist"));
            doGet(request, response);
        } else if (userExist(user, userDao)) {
            request.setAttribute("EmailError", bundle.getString("user_exist"));
            doGet(request, response);
        } else {
            try {
                applicantDao.create(applicant);
                int id = applicantDao.findEntityId(applicant);
                user.setApplicantId(id);
                userDao.create(user);
            } catch (DaoException e) {
                // add log
                throw new RuntimeException(e);
            }
        }
    }

    private boolean applicantExist(Applicant applicant, ApplicantDao applicantDao) {
        int id = 0;
        try {
            id = applicantDao.findEntityId(applicant);
        } catch (DaoException e) {
            return false;
        }
        return id > 0;
    }

    private boolean userExist(User user, UserDao userDao) {
        try {
            return userDao.isExist(user.getLogin(), user.getPassword());
        } catch (DaoException e) {
            return false;
        }
    }

    private void setUserAndApplicant(HttpServletRequest request, HttpServletResponse response, User user, Applicant applicant)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        checkEmail(request, response, email);
        String password = request.getParameter("psw");
        checkPassword(request, response, password);
        String repeatPassword = request.getParameter("psw-repeat");
        checkRepeatPassword(request, response, password, repeatPassword);
        String lastname = request.getParameter("lastname");
        checkLastName(request, response, lastname);
        String firstname = request.getParameter("firstname");
        checkFirstName(request, response, firstname);
        String surname = request.getParameter("surname");
        checkSurname(request, response, surname);
        String city = request.getParameter("city");
        checkCity(request, response, city);
        String region = request.getParameter("region");
        checkRegion(request, response, region);
        String institution = request.getParameter("education");
        checkEducationInstitution(request, response, institution);

        //initialise
        user.setLogin(email);
        user.setPassword(password);
        user.setRole(Role.APPLICANT);

        applicant.setEmail(email);
        applicant.setLastName(lastname);
        applicant.setName(firstname);
        applicant.setSurname(surname);
        applicant.setCity(city);
        applicant.setRegion(region);
        applicant.setNameEducationalInstitution(institution);
    }

    private void checkEmail(HttpServletRequest request, HttpServletResponse response, String email)
            throws ServletException, IOException {
        if (!Validator.checkEmail(email)) {
            request.setAttribute("EmailError", bundle.getString("email_error"));
            doGet(request, response);
        }
    }

    private void checkPassword(HttpServletRequest request, HttpServletResponse response, String password)
            throws ServletException, IOException {
        if (!Validator.checkPassword(password)) {
            request.setAttribute("PasswordError", bundle.getString("password_error"));
            doGet(request, response);
        }
    }

    private void checkRepeatPassword(HttpServletRequest request, HttpServletResponse response,
                                     String password, String passwordRepeat)
            throws ServletException, IOException {
        if (!password.equals(passwordRepeat)) {
            request.setAttribute("PasswordRepeatError", bundle.getString("password_repeat_error"));
            doGet(request, response);
        }
    }

    private void checkLastName(HttpServletRequest request, HttpServletResponse response, String lastname)
            throws ServletException, IOException {
        if (!Validator.checkName(lastname)) {
            request.setAttribute("LastNameError", bundle.getString("last_name_error"));
            doGet(request, response);
        }
    }

    private void checkFirstName(HttpServletRequest request, HttpServletResponse response, String firstname)
            throws ServletException, IOException {
        if (!Validator.checkName(firstname)) {
            request.setAttribute("FirstNameError", bundle.getString("first_name_error"));
            doGet(request, response);
        }
    }

    private void checkSurname(HttpServletRequest request, HttpServletResponse response, String surname)
            throws ServletException, IOException {
        if (!Validator.checkName(surname)) {
            request.setAttribute("SurnameError", bundle.getString("surname_error"));
            doGet(request, response);
        }
    }

    private void checkCity(HttpServletRequest request, HttpServletResponse response, String city)
            throws ServletException, IOException {
        if (!Validator.checkName(city)) {
            request.setAttribute("CityError", bundle.getString("city_error"));
            doGet(request, response);
        }
    }

    private void checkRegion(HttpServletRequest request, HttpServletResponse response, String region)
            throws ServletException, IOException {
        if (!Validator.checkName(region)) {
            request.setAttribute("RegionError", bundle.getString("region_error"));
            doGet(request, response);
        }
    }

    private void checkEducationInstitution(HttpServletRequest request, HttpServletResponse response, String institution)
            throws ServletException, IOException {
        if (!Validator.checkInstitutionName(institution)) {
            request.setAttribute("InstitutionError", bundle.getString("institution_error"));
            doGet(request, response);
        }
    }

    private ResourceBundle getResourceBundle(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("lang");
        if (locale.length() > 0) {
            String[] lang = locale.split("_");
            return ResourceBundle.getBundle("locales.content", new Locale(lang[0], lang[1]));
        }
        return ResourceBundle.getBundle("locales.content", new Locale(locale));
    }
}
