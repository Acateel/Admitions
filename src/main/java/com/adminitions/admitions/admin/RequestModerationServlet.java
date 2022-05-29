package com.adminitions.admitions.admin;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RequestModerationServlet", value = "/RequestModeration")
public class RequestModerationServlet extends HttpServlet {

    private transient FacultyDao facultyDao;
    private transient RequestDao requestDao;

    @Override
    public void init() throws ServletException {
        requestDao = (RequestDao) getServletContext().getAttribute("RequestDao");
        facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getParameter("faculty_id") != null) {
                int facultiesId = Integer.parseInt(request.getParameter("faculty_id"));
                request.setAttribute("faculty", facultyDao.findEntityById(facultiesId));
                request.getSession().setAttribute("faculty", facultyDao.findEntityById(facultiesId));
                request.setAttribute("requests", requestDao.findAllWithFaculty(facultiesId));
                request.getRequestDispatcher("WEB-INF/admin_panels/request_moderation.jsp").forward(request, response);
            } else {
                response.sendRedirect("FacultyModeration");
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
            // add log and error page
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
