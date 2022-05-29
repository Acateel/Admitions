package com.adminitions.admitions.auth;

import com.adminitions.data_access.ApplicantDao;
import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.UserDao;
import com.adminitions.entities.Applicant;
import com.adminitions.entities.users.Role;
import com.adminitions.entities.users.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationServletTest {

    RegistrationServlet servlet;
    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher dispatcher;
    HttpSession session;
    String path = "WEB-INF/auth/register.jsp";

    ApplicantDao applicantDao;
    UserDao userDao;
    Applicant applicant;
    User user;
    @BeforeEach
    void setUp() throws DaoException {
        servlet = new RegistrationServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        session = mock(HttpSession.class);
        setApplicant();
        setUser();
        setDao();
        when(session.getAttribute("lang")).thenReturn("uk_UA");
        when(request.getSession()).thenReturn(session);
        setGetParameter();
    }

    private void setDao() throws DaoException {
        applicantDao = mock(ApplicantDao.class);
        userDao = mock(UserDao.class);
        servlet.applicantDao = applicantDao;
        servlet.userDao = userDao;
        when(applicantDao.findEntityId(applicant)).thenReturn(applicant.getId());
        when(applicantDao.findEntityId(applicant)).thenReturn(1);
        when(userDao.isExist(user.getLogin(), user.getPassword())).thenReturn(false);
    }

    private void setGetParameter() {
        when(request.getParameter("email")).thenReturn(applicant.getEmail());
        when(request.getParameter("psw")).thenReturn(user.getPassword());
        when(request.getParameter("psw-repeat")).thenReturn(user.getPassword());
        when(request.getParameter("lastname")).thenReturn(applicant.getLastName());
        when(request.getParameter("firstname")).thenReturn(applicant.getName());
        when(request.getParameter("surname")).thenReturn(applicant.getSurname());
        when(request.getParameter("city")).thenReturn(applicant.getCity());
        when(request.getParameter("region")).thenReturn(applicant.getRegion());
        when(request.getParameter("education")).thenReturn(applicant.getNameEducationalInstitution());
    }

    private void setUser() {
        user = new User();
        user.setLogin("example@gmail.com");
        user.setPassword("sometext1");
        user.setRole(Role.APPLICANT);
        user.setApplicantId(0);
    }

    private void setApplicant() {
        applicant = new Applicant();
        applicant.setId(0);
        applicant.setEmail("example@gmail.com");
        applicant.setName("name");
        applicant.setLastName("lastname");
        applicant.setSurname("surname");
        applicant.setCity("city");
        applicant.setRegion("region");
        applicant.setNameEducationalInstitution("education");
    }

    @Test
    void doGet() throws ServletException, IOException {
        servlet.doGet(request,response);
        verify(dispatcher).forward(request,response);
    }

    @Test
    void doPost() throws ServletException, IOException, DaoException {
        ResourceBundle bundle = ResourceBundle.getBundle("locales.content", new Locale("uk", "UA"));
        servlet.doPost(request, response);

        verify(userDao).create(user);
        verify(response).sendRedirect("index.jsp");
    }
}