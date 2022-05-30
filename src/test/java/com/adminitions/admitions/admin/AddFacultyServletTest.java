package com.adminitions.admitions.admin;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.entities.Faculty;
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

class AddFacultyServletTest {
    AddFacultyServlet servlet;
    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher dispatcher;

    FacultyDao facultyDao;
    Faculty faculty;
    @BeforeEach
    void setUp() {
        setFaculty();
        servlet = new AddFacultyServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("WEB-INF/admin_panels/add_faculty.jsp")).thenReturn(dispatcher);
        when(request.getParameter("faculty_name")).thenReturn(faculty.getName());
        when(request.getParameter("budget_seats")).thenReturn(String.valueOf(faculty.getBudgetSeats()));
        when(request.getParameter("total_seats")).thenReturn(String.valueOf(faculty.getTotalSeats()));

        facultyDao = mock(FacultyDao.class);
        servlet.facultyDao = facultyDao;

    }

    private void setFaculty() {
        faculty = new Faculty();
        faculty.setName("name");
        faculty.setBudgetSeats(5);
        faculty.setTotalSeats(10);
    }

    @Test
    void doGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost() throws ServletException, IOException, DaoException {
        servlet.doPost(request, response);
        verify(response).sendRedirect("FacultyModeration");
        verify(facultyDao).create(faculty);
    }
}