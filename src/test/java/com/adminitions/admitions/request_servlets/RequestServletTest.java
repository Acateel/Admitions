package com.adminitions.admitions.request_servlets;

import com.adminitions.admitions.auth.RegistrationServlet;
import com.adminitions.data_access.ApplicantDao;
import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;
import com.adminitions.entities.Applicant;
import com.adminitions.entities.Faculty;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RequestServletTest {

    RequestServlet servlet;
    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher dispatcher;
    HttpSession session;
    String path = "/WEB-INF/requests/requests.jsp";

    FacultyDao facultyDao;
    RequestDao requestDao;
    ApplicantDao applicantDao;
    Applicant applicant;
    User user;

    @BeforeEach
    void setUp() throws DaoException {
        servlet = new RequestServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        when(request.getRequestDispatcher("WEB-INF/requests/send_request.jsp")).thenReturn(dispatcher);
        when(request.getParameter("faculty_id")).thenReturn("1");

        setApplicant();
        setUser();
        setSession();

        facultyDao = mock(FacultyDao.class);
        servlet.facultyDao = facultyDao;
        when(facultyDao.findEntityById(1)).thenReturn(new Faculty());

        requestDao = mock(RequestDao.class);
        servlet.requestDao = requestDao;
        when(requestDao.findAllWithFaculty(1)).thenReturn(new ArrayList<>());
        when(requestDao.requestExist(1, user.getId())).thenReturn(false);

        applicantDao = mock(ApplicantDao.class);
        servlet.applicantDao = applicantDao;
        when(applicantDao.findEntityById(1)).thenReturn(applicant);
    }

    private void setSession() {
        session = mock(HttpSession.class);
        when(session.getAttribute("lang")).thenReturn("uk_UA");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("User")).thenReturn(user);
    }

    private void setUser() {
        user = new User();
        user.setLogin("example@gmail.com");
        user.setPassword("sometext1");
        user.setRole(Role.APPLICANT);
        user.setApplicantId(1);
    }

    private void setApplicant() {
        applicant = new Applicant();
        applicant.setId(1);
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
        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost() throws ServletException, IOException {
        servlet.doPost(request, response);
        verify(dispatcher).forward(request,response);
    }
}