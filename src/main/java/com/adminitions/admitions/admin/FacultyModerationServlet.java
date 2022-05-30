package com.adminitions.admitions.admin;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "FacultyModerationServlet", value = "/FacultyModeration")
public class FacultyModerationServlet extends HttpServlet {
    protected transient FacultyDao facultyDao;

    @Override
    public void init() throws ServletException {
        facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("faculties", facultyDao.findAll());
            request.getRequestDispatcher("WEB-INF/admin_panels/facultiest_moderation.jsp").forward(request, response);
        } catch (DaoException e) {
            throw new RuntimeException(e);
            // add log and error page
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        switch (type){
            case "delete":
                deleteFaculty(request, response);
                break;
            case "change":
                response.sendRedirect("ChangeFaculty?faculty_id="+request.getParameter("faculty_id"));
                break;
            case "add":
                response.sendRedirect("AddFaculty");
                break;
            default:
                doGet(request, response);
                break;
        }
    }

    private void deleteFaculty(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int facultyId = Integer.parseInt(request.getParameter("faculty_id"));
        try {
            facultyDao.delete(facultyId);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        doGet(request, response);
    }
}
