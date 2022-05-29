package com.adminitions.admitions.request_servlets;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.RequestDao;
import com.adminitions.entities.Faculty;
import com.adminitions.entities.users.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SendRequestServletTest {

    SendRequestServlet servlet;
    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher dispatcher;
    HttpSession session;
    String path = "WEB-INF/requests/send_request.jsp";
    RequestDao requestDao;

    Faculty faculty;

    @BeforeEach
    void setUp() {
        servlet = new SendRequestServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        when(request.getRequestDispatcher("index.jsp")).thenReturn(dispatcher);
        setFaculty();
        setParameter();
        setSession();
        requestDao = mock(RequestDao.class);
        servlet.requestDao = requestDao;
        when(requestDao.requestExist(0,0)).thenReturn(false);
    }

    private void setFaculty() {
        faculty = new Faculty();
        faculty.setName("name");
        faculty.setBudgetSeats(1);
        faculty.setTotalSeats(2);
    }

    private void setParameter() {
        when(request.getParameter("main_subject")).thenReturn("150");
        when(request.getParameter("second_subject")).thenReturn("150");
        when(request.getParameter("sub_subject")).thenReturn("150");
        when(request.getParameter("average_attestation_score")).thenReturn("10.0");
    }

    private void setSession() {
        session = mock(HttpSession.class);
        when(session.getAttribute("lang")).thenReturn("uk_UA");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("User")).thenReturn(new User());
        when(session.getAttribute("faculty")).thenReturn(faculty);
    }

    @Test
    void doGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost() throws ServletException, IOException, DaoException {
        servlet.doPost(request, response);
        verify(requestDao).create(anyObject());
        verify(dispatcher).forward(request, response);
    }
}