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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet(name = "RegistrationServlet", value = "/Registration")
public class RegistrationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RegistrationServlet.class);
    protected transient ResourceBundle bundle;
    protected transient UserDao userDao;
    protected transient ApplicantDao applicantDao;
    private static final String EMAIL_ERROR_MESSAGE_KEY = "EmailError";

    @Override
    public void init() throws ServletException {
        logger.info("Initialize registration servlet");
        //initialise dao classes
        userDao = (UserDao) getServletContext().getAttribute("UserDao");
        applicantDao = (ApplicantDao) getServletContext().getAttribute("ApplicantDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Registration GET method");
        //redirects to the registration page
        request.getRequestDispatcher("WEB-INF/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Registration POST method");
        User user = new User();
        Applicant applicant = new Applicant();
        bundle = getResourceBundle(request);
        // if check complete and user and applicant initialise
        if(setUserAndApplicant(request, response, user, applicant)) {
            addToDb(request, response, user, applicant);
            // add user and applicant full name into session
            HttpSession session = request.getSession();
            session.setAttribute("User", user);
            String fullName = applicant.getLastName() + " " + applicant.getName();
            session.setAttribute("Name", fullName);
        }
        // redirects to the main page
        response.sendRedirect("index.jsp");
    }


    private void addToDb(HttpServletRequest request, HttpServletResponse response, User user, Applicant applicant)
            throws ServletException, IOException {
        // if applicant with this email exist in DB
        if (applicantExist(applicant, applicantDao)) {
            // send error and redirects to registration page
            request.setAttribute(EMAIL_ERROR_MESSAGE_KEY, bundle.getString("applicant_exist"));
            doGet(request, response);
        } else if (userExist(user, userDao)) {
            // if user with this email exist in DB
            // send error and redirects to registration page
            request.setAttribute(EMAIL_ERROR_MESSAGE_KEY, bundle.getString("user_exist"));
            doGet(request, response);
        } else {
            try {
                // add applicant and user into DB
                applicantDao.create(applicant);
                int id = applicantDao.findEntityId(applicant);
                user.setApplicantId(id);
                userDao.create(user);
            } catch (DaoException e) {
                logger.error("Registration DaoException");
            }
        }
    }

    private boolean applicantExist(Applicant applicant, ApplicantDao applicantDao) {
        int id;
        try {
            // get id
            id = applicantDao.findEntityId(applicant);
        } catch (DaoException e) {
            // if applicant with this id not found
            return false;
        }
        return id > 0;
    }

    private boolean userExist(User user, UserDao userDao) {
        try {
            return userDao.isExist(user.getLogin(), user.getPassword());
        } catch (DaoException e) {
            logger.error("User exist DaoException");
            return false;
        }
    }

    private boolean setUserAndApplicant(HttpServletRequest request, HttpServletResponse response, User user, Applicant applicant)
            throws ServletException, IOException {
        // take data from request parameters
        String email = request.getParameter("email");
        String password = request.getParameter("psw");
        String repeatPassword = request.getParameter("psw-repeat");
        String lastname = request.getParameter("lastname");
        String firstname = request.getParameter("firstname");
        String surname = request.getParameter("surname");
        String city = request.getParameter("city");
        String region = request.getParameter("region");
        String institution = request.getParameter("education");

        //get data in request
        request.setAttribute("email", email);
        request.setAttribute("psw", password);
        request.setAttribute("psw_repeat", repeatPassword);
        request.setAttribute("lastname", lastname);
        request.setAttribute("firstname", firstname);
        request.setAttribute("surname", surname);
        request.setAttribute("city", city);
        request.setAttribute("region", region);
        request.setAttribute("education", institution);

        // if check failure redirects into registration with error message
        boolean check1 = checkEmail(request, response, email);
        boolean check2 = checkPassword(request, response, password);
        boolean check3 = checkRepeatPassword(request, response, password, repeatPassword);
        boolean check4 = checkLastName(request, response, lastname);
        boolean check5 = checkFirstName(request, response, firstname);
        boolean check6 = checkSurname(request, response, surname);
        boolean check7 = checkCity(request, response, city);
        boolean check8 = checkRegion(request, response, region);
        boolean check9 = checkEducationInstitution(request, response, institution);

        if (check1 && check2 && check3 && check4 && check5 && check6 && check7 && check8 && check9) {
            //initialise if all check completed
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
            return true;
        } else {
            return false;
        }
    }

    private boolean checkEmail(HttpServletRequest request, HttpServletResponse response, String email)
            throws ServletException, IOException {
        if (!Validator.checkEmail(email)) {
            request.setAttribute(EMAIL_ERROR_MESSAGE_KEY, bundle.getString("email_error"));
            doGet(request, response);
            return false;
        }
        return true;
    }

    private boolean checkPassword(HttpServletRequest request, HttpServletResponse response, String password)
            throws ServletException, IOException {
        if (!Validator.checkPassword(password)) {
            request.setAttribute("PasswordError", bundle.getString("password_error"));
            doGet(request, response);
            return false;
        }
        return true;
    }

    private boolean checkRepeatPassword(HttpServletRequest request, HttpServletResponse response,
                                        String password, String passwordRepeat)
            throws ServletException, IOException {
        if (!password.equals(passwordRepeat)) {
            request.setAttribute("PasswordRepeatError", bundle.getString("password_repeat_error"));
            doGet(request, response);
            return false;
        }
        return true;
    }

    private boolean checkLastName(HttpServletRequest request, HttpServletResponse response, String lastname)
            throws ServletException, IOException {
        if (!Validator.checkName(lastname)) {
            request.setAttribute("LastNameError", bundle.getString("last_name_error"));
            doGet(request, response);
            return false;
        }
        return true;
    }

    private boolean checkFirstName(HttpServletRequest request, HttpServletResponse response, String firstname)
            throws ServletException, IOException {
        if (!Validator.checkName(firstname)) {
            request.setAttribute("FirstNameError", bundle.getString("first_name_error"));
            doGet(request, response);
            return false;
        }
        return true;
    }

    private boolean checkSurname(HttpServletRequest request, HttpServletResponse response, String surname)
            throws ServletException, IOException {
        if (!Validator.checkName(surname)) {
            request.setAttribute("SurnameError", bundle.getString("surname_error"));
            doGet(request, response);
            return false;
        }
        return true;
    }

    private boolean checkCity(HttpServletRequest request, HttpServletResponse response, String city)
            throws ServletException, IOException {
        if (!Validator.checkName(city)) {
            request.setAttribute("CityError", bundle.getString("city_error"));
            doGet(request, response);
            return false;
        }
        return true;
    }

    private boolean checkRegion(HttpServletRequest request, HttpServletResponse response, String region)
            throws ServletException, IOException {
        if (!Validator.checkName(region)) {
            request.setAttribute("RegionError", bundle.getString("region_error"));
            doGet(request, response);
            return false;
        }
        return true;
    }

    private boolean checkEducationInstitution(HttpServletRequest request, HttpServletResponse response, String institution)
            throws ServletException, IOException {
        if (!Validator.checkInstitutionName(institution)) {
            request.setAttribute("InstitutionError", bundle.getString("institution_error"));
            doGet(request, response);
            return false;
        }
        return true;
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
