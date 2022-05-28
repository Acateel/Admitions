package com.adminitions.admitions.request_servlets;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RequestServlet", value = "/Request")
public class RequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(request.getParameter("faculty_id") != null) {
                int facultiesId = Integer.parseInt(request.getParameter("faculty_id"));
                FacultyDao facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
                request.setAttribute("faculty", facultyDao.findEntityById(facultiesId));
                RequestDao requestDao = (RequestDao) getServletContext().getAttribute("RequestDao");
                request.setAttribute("requests", requestDao.findAllWithFaculty(facultiesId));
                request.getRequestDispatcher("/WEB-INF/requests/requests.jsp").forward(request, response);
            }
            else{
                response.sendRedirect("Faculty");
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
            // add log and error page
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
