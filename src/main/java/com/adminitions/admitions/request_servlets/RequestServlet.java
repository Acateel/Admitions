package com.adminitions.admitions.request_servlets;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet(name = "RequestServlet", value = "/Request")
public class RequestServlet extends HttpServlet {

    private ResourceBundle bundle;
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
        bundle = getResourceBundle(request);
        Object userData = request.getSession().getAttribute("User");
        if(userData == null){
            request.setAttribute("SendRequestError", bundle.getString("send_request_without_auth"));
        }
        doGet(request, response);
    }

    private ResourceBundle getResourceBundle(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("lang");
        if(locale.length() > 0){
            String[] lamgs = locale.split("_");
            return ResourceBundle.getBundle("locales.content", new Locale(lamgs[0], lamgs[1]));
        }
        return ResourceBundle.getBundle("locales.content", new Locale(locale));
    }
}
