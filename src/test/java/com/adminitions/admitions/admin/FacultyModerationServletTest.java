package com.adminitions.admitions.admin;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacultyModerationServletTest {

    FacultyModerationServlet servlet;
    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher dispatcher;

    FacultyDao facultyDao;
    @BeforeEach
    void setUp() throws DaoException {
        servlet = new FacultyModerationServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("WEB-INF/admin_panels/facultiest_moderation.jsp")).thenReturn(dispatcher);
        when(request.getParameter("faculty_id")).thenReturn("1");

        facultyDao = mock(FacultyDao.class);
        servlet.facultyDao = facultyDao;
        when(facultyDao.findAll()).thenReturn(new ArrayList<>());
    }

    @Test
    void doGet() throws ServletException, IOException, DaoException {
        servlet.doGet(request, response);

        verify(facultyDao).findAll();
        verify(request).setAttribute("faculties", facultyDao.findAll());
        verify(dispatcher).forward(request,response);
    }

    @Test
    void doPost_delete() throws ServletException, IOException, DaoException {
        when(request.getParameter("type")).thenReturn("delete");
        servlet.doPost(request,response);
        verify(facultyDao).delete(1);
    }

    @Test
    void doPost_change() throws ServletException, IOException, DaoException {
        when(request.getParameter("type")).thenReturn("change");
        servlet.doPost(request,response);
        verify(response).sendRedirect("ChangeFaculty?faculty_id="+request.getParameter("faculty_id"));
    }

    @Test
    void doPost_add() throws ServletException, IOException, DaoException {
        when(request.getParameter("type")).thenReturn("add");
        servlet.doPost(request,response);
        verify(response).sendRedirect("AddFaculty");
    }
}