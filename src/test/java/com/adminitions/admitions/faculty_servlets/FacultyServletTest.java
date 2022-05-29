package com.adminitions.admitions.faculty_servlets;

import com.adminitions.admitions.auth.RegistrationServlet;
import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacultyServletTest {

    FacultyServlet servlet;
    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher dispatcher;
    String path = "/WEB-INF/faculty/show_faculties.jsp";

    FacultyDao facultyDao;

    @BeforeEach
    void setUp() {
        servlet = new FacultyServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        facultyDao = mock(FacultyDao.class);
        servlet.facultyDao = facultyDao;
    }

    @Test
    void doGet() throws ServletException, IOException, DaoException {
        servlet.doGet(request, response);
        verify(facultyDao).findAll();
        verify(dispatcher).forward(request,response);
    }

    @Test
    void doPost_byId() throws ServletException, IOException, DaoException {
        when(request.getParameter("order")).thenReturn("byId");
        servlet.doPost(request, response);
        verify(facultyDao).findAll();
        verify(dispatcher).forward(request,response);
    }

    @Test
    void doPost_byName() throws ServletException, IOException, DaoException {
        when(request.getParameter("order")).thenReturn("byName");
        servlet.doPost(request, response);
        verify(facultyDao).findAllByName();
        verify(dispatcher).forward(request,response);
    }

    @Test
    void doPost_byNameRevers() throws ServletException, IOException, DaoException {
        when(request.getParameter("order")).thenReturn("byNameRevers");
        servlet.doPost(request, response);
        verify(facultyDao).findAllByNameRevers();
        verify(dispatcher).forward(request,response);
    }

    @Test
    void doPost_byBudget() throws ServletException, IOException, DaoException {
        when(request.getParameter("order")).thenReturn("byBudget");
        servlet.doPost(request, response);
        verify(facultyDao).findAllByBudget();
        verify(dispatcher).forward(request,response);
    }

    @Test
    void doPost_byTotal() throws ServletException, IOException, DaoException {
        when(request.getParameter("order")).thenReturn("byTotal");
        servlet.doPost(request, response);
        verify(facultyDao).findAllByTotal();
        verify(dispatcher).forward(request,response);
    }

    @Test
    void doPost_default() throws ServletException, IOException, DaoException {
        when(request.getParameter("order")).thenReturn("");
        servlet.doPost(request, response);
        verify(facultyDao).findAll();
        verify(dispatcher).forward(request,response);
    }
}