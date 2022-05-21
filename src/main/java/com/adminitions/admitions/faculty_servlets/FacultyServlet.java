package com.adminitions.admitions.faculty_servlets;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "FacultyServlet", value = "/FacultyServlet")
public class FacultyServlet extends HttpServlet {
    private transient FacultyDao facultyDao;

    @Override
    public void init() throws ServletException {
        facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("faculties", facultyDao.findAll());
            request.getRequestDispatcher("/WEB-INF/faculty/show_faculties.jsp").forward(request, response);
        } catch (DaoException e) {
            throw new RuntimeException(e);
            // add log and error page
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
