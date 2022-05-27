package com.adminitions.admitions.request_servlets;

import com.adminitions.data_access.DaoException;
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
            int facultiesId = (int)request.getAttribute("faculty_id");
            RequestDao requestDao = (RequestDao) getServletContext().getAttribute("RequestDao");
            request.setAttribute("requests", requestDao.findAllWithFaculty(facultiesId));
            request.getRequestDispatcher("/WEB-INF/requests/requests.jsp").forward(request, response);
        } catch (DaoException e) {
            throw new RuntimeException(e);
            // add log and error page
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
