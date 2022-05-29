package com.adminitions.admitions.admin;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.entities.Faculty;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ChangeFacultyServlet", value = "/ChangeFaculty")
public class ChangeFacultyServlet extends HttpServlet {
    private transient FacultyDao facultyDao;

    @Override
    public void init() throws ServletException {
        facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int facultyId = Integer.parseInt(request.getParameter("faculty_id"));
        try {
            Faculty faculty = facultyDao.findEntityById(facultyId);
            request.setAttribute("faculty", faculty);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("WEB-INF/admin_panels/change_faculty.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
