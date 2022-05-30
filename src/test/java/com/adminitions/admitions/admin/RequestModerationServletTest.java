package com.adminitions.admitions.admin;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;
import com.adminitions.entities.Faculty;
import com.adminitions.entities.request.Request;
import com.adminitions.entities.request.RequestStatus;
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

class RequestModerationServletTest {

    RequestModerationServlet servlet;
    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher dispatcher;

    FacultyDao facultyDao;
    Faculty faculty;
    RequestDao requestDao;
    Request sendRequest;
    HttpSession session;
    @BeforeEach
    void setUp() throws DaoException {
        servlet = new RequestModerationServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);
        when(request.getRequestDispatcher("WEB-INF/admin_panels/request_moderation.jsp")).thenReturn(dispatcher);
        when(request.getParameter("faculty_id")).thenReturn("1");
        when(request.getParameter("applicant_id")).thenReturn("1");
        when(request.getSession()).thenReturn(session);

        setFaculty();
        setRequest();

        facultyDao = mock(FacultyDao.class);
        servlet.facultyDao = facultyDao;
        when(facultyDao.findEntityById(1)).thenReturn(faculty);


        requestDao = mock(RequestDao.class);
        servlet.requestDao = requestDao;
        when(requestDao.findAllWithFaculty(1)).thenReturn(new ArrayList<>());
        when(requestDao.findRequestByIds(0,1)).thenReturn(sendRequest);
    }

    private void setRequest() {
        sendRequest = new Request();
        sendRequest.setStatus(RequestStatus.NOT_PROCESSED);
    }

    private void setFaculty() {
        faculty = new Faculty();
        faculty.setId(1);
    }

    @Test
    void doGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost() throws ServletException, IOException, DaoException {
        servlet.doPost(request, response);
        verify(requestDao).findRequestByIds(0,1);
        verify(requestDao).update(sendRequest);
    }
}