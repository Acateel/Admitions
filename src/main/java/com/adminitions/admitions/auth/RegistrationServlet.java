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

@WebServlet(name = "RegistrationServlet", value = "/Registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        Applicant applicant = new Applicant();
        setUserAndApplicant(request, response, user, applicant);

        UserDao userDao = (UserDao) getServletContext().getAttribute("UserDao");
        ApplicantDao applicantDao = (ApplicantDao) getServletContext().getAttribute("ApplicantDao");

        if (applicantExist(applicant, applicantDao)) {
            request.setAttribute("EmailError", "This applicant exist");
            doGet(request, response);
        } else if (userExist(user, userDao)) {
            request.setAttribute("EmailError", "This user exist");
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

    private boolean userExist(User user, UserDao userDao){
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
            request.setAttribute("EmailError", "Email is not format");
            doGet(request, response);
        }
    }

    private void checkPassword(HttpServletRequest request, HttpServletResponse response, String password)
            throws ServletException, IOException {
        if (!Validator.checkPassword(password)) {
            request.setAttribute("PasswordError", "Minimum eight characters, at least one letter and one number");
            doGet(request, response);
        }
    }

    private void checkRepeatPassword(HttpServletRequest request, HttpServletResponse response,
                                     String password, String passwordRepeat)
            throws ServletException, IOException {
        if (!password.equals(passwordRepeat)) {
            request.setAttribute("PasswordRepeatError", "Passwords isn`t one the same");
            doGet(request, response);
        }
    }

    private void checkLastName(HttpServletRequest request, HttpServletResponse response, String lastname)
            throws ServletException, IOException {
        if (!Validator.checkName(lastname)) {
            request.setAttribute("LastNameError", "Last name is not format");
            doGet(request, response);
        }
    }

    private void checkFirstName(HttpServletRequest request, HttpServletResponse response, String firstname)
            throws ServletException, IOException {
        if (!Validator.checkName(firstname)) {
            request.setAttribute("FirstNameError", "First name is not format");
            doGet(request, response);
        }
    }

    private void checkSurname(HttpServletRequest request, HttpServletResponse response, String surname)
            throws ServletException, IOException {
        if (!Validator.checkName(surname)) {
            request.setAttribute("SurnameError", "Surname is not format");
            doGet(request, response);
        }
    }

    private void checkCity(HttpServletRequest request, HttpServletResponse response, String city)
            throws ServletException, IOException {
        if (!Validator.checkName(city)) {
            request.setAttribute("CityError", "City is not format");
            doGet(request, response);
        }
    }

    private void checkRegion(HttpServletRequest request, HttpServletResponse response, String region)
            throws ServletException, IOException {
        if (!Validator.checkName(region)) {
            request.setAttribute("RegionError", "Region is not format");
            doGet(request, response);
        }
    }

    private void checkEducationInstitution(HttpServletRequest request, HttpServletResponse response, String institution)
            throws ServletException, IOException {
        if (!Validator.checkInstitutionName(institution)) {
            request.setAttribute("InstitutionError", "Institution is not format");
            doGet(request, response);
        }
    }
}
