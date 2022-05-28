package com.adminitions.admitions.request_servlets;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;
import com.adminitions.entities.users.Role;
import com.adminitions.entities.users.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet(name = "RequestServlet", value = "/Request")
public class RequestServlet extends HttpServlet {

    private static final String REQUEST_CHECK_ERROR = "SendRequestError";
    private ResourceBundle bundle;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(request.getParameter("faculty_id") != null) {
                int facultiesId = Integer.parseInt(request.getParameter("faculty_id"));
                FacultyDao facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
                request.setAttribute("faculty", facultyDao.findEntityById(facultiesId));
                request.getSession().setAttribute("faculty", facultyDao.findEntityById(facultiesId));
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
        checkUser(request, response);
        request.getRequestDispatcher("WEB-INF/requests/send_request.jsp").forward(request, response);
    }

    private void checkUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object userData = request.getSession().getAttribute("User");
        checkAuth(request, response, userData);
        User user = (User)userData;
        Role userRole = getRole(user);
        checkRole(request, response, userRole);
        checkTwiceSend(request, response, user);
    }

    private void checkTwiceSend(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        RequestDao requestDao = (RequestDao) getServletContext().getAttribute("RequestDao");
        int facultyId = Integer.parseInt(request.getParameter("faculty_id"));
        boolean exist = requestDao.requestExist(facultyId, user.getId());
        if(exist){
            request.setAttribute(REQUEST_CHECK_ERROR, bundle.getString("send_request_exist"));
            doGet(request, response);
        }
    }

    private void checkRole(HttpServletRequest request, HttpServletResponse response, Role userRole)
            throws ServletException, IOException {
        if(userRole != Role.APPLICANT){
            request.setAttribute(REQUEST_CHECK_ERROR, bundle.getString("send_request_not_applicant"));
            doGet(request, response);
        }
    }

    private void checkAuth(HttpServletRequest request, HttpServletResponse response, Object userData)
            throws ServletException, IOException {
        if(userData == null){
            request.setAttribute(REQUEST_CHECK_ERROR, bundle.getString("send_request_without_auth"));
            doGet(request, response);
        }
    }

    private Role getRole(User user) {
        Role userRole = Role.UNKNOWN;
        if(user !=null){
            userRole = user.getRole();
        }
        return userRole;
    }

    private ResourceBundle getResourceBundle(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("lang");
        if(locale.length() > 0){
            String[] lamgs = locale.split("_");
            return ResourceBundle.getBundle("locales.content", new Locale(lamgs[0], lamgs[1]));
        }
        else{
            return ResourceBundle.getBundle("locales.content");
        }
    }
}
