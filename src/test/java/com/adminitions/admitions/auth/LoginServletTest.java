package com.adminitions.admitions.auth;

import com.adminitions.data_access.ApplicantDao;
import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.UserDao;
import com.adminitions.entities.Applicant;
import com.adminitions.entities.users.Role;
import com.adminitions.entities.users.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class LoginServletTest {

    private final static String LOGIN_PATH = "WEB-INF/auth/login.jsp";

    @Test
    void doGet() throws ServletException, IOException {
        LoginServlet servlet = new LoginServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(LOGIN_PATH)).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(LOGIN_PATH);
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_false() throws DaoException, ServletException, IOException {
        LoginServlet servlet = new LoginServlet();
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("lang")).thenReturn("");
        HttpServletRequest request = mock(HttpServletRequest.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(LOGIN_PATH)).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("login")).thenReturn("login");
        when(request.getParameter("password")).thenReturn("password");

        HttpServletResponse response = mock(HttpServletResponse.class);

        ResourceBundle resourceBundle = ResourceBundle.getBundle("locales.content");
        UserDao userDao = mock(UserDao.class);

        ApplicantDao applicantDao = mock(ApplicantDao.class);

        servlet.userDao = userDao;
        when(userDao.isExist("login", "password")).thenReturn(false);

        servlet.doPost(request, response);

        verify(request, times(1)).setAttribute("Error", resourceBundle.getString("login_error"));
    }

    @Test
    void doPost_true() throws DaoException, ServletException, IOException {
        LoginServlet servlet = new LoginServlet();
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("lang")).thenReturn("uk_UA");
        HttpServletRequest request = mock(HttpServletRequest.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(LOGIN_PATH)).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("login")).thenReturn("login");
        when(request.getParameter("password")).thenReturn("password");

        HttpServletResponse response = mock(HttpServletResponse.class);

        ResourceBundle resourceBundle = ResourceBundle.getBundle("locales.content", new Locale("uk","UA"));
        UserDao userDao = mock(UserDao.class);
        ApplicantDao applicantDao = mock(ApplicantDao.class);

        User user = new User();
        user.setApplicantId(1);
        user.setRole(Role.APPLICANT);
        servlet.userDao = userDao;
        when(userDao.isExist("login", "password")).thenReturn(true);
        when(userDao.findUser("login", "password")).thenReturn(user);

        servlet.applicantDao = applicantDao;
        Applicant applicant = new Applicant();
        applicant.setLastName("last");
        applicant.setName("name");
        when(applicantDao.findEntityById(1)).thenReturn(applicant);

        servlet.doPost(request, response);

        verify(session, times(1)).setAttribute("Name", "last name");
        verify(session, times(1)).setAttribute("User", user);
        verify(response, times(1)).sendRedirect("index.jsp");
    }
}