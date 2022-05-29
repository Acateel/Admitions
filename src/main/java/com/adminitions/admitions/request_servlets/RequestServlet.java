package com.adminitions.admitions.request_servlets;

import com.adminitions.data_access.*;
import com.adminitions.entities.Applicant;
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
    private transient ResourceBundle bundle;

    protected transient FacultyDao facultyDao;
    protected transient RequestDao requestDao;

    protected transient  ApplicantDao applicantDao;

    @Override
    public void init() throws ServletException {
        requestDao = (RequestDao) getServletContext().getAttribute("RequestDao");
        facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
        applicantDao = (ApplicantDao) getServletContext().getAttribute("ApplicantDao");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(request.getParameter("faculty_id") != null) {
                int facultiesId = Integer.parseInt(request.getParameter("faculty_id"));
                request.setAttribute("faculty", facultyDao.findEntityById(facultiesId));
                request.getSession().setAttribute("faculty", facultyDao.findEntityById(facultiesId));
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
        checkBlock(request, response, user);
        checkTwiceSend(request, response, user);
    }

    private void checkBlock(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        try {
            Applicant applicant = applicantDao.findEntityById(user.getApplicantId());
            if (applicant.isBlock()) {
                request.setAttribute(REQUEST_CHECK_ERROR, bundle.getString("applicant_blocked"));
                doGet(request, response);
            }
        }
        catch (DaoException exception){
            request.setAttribute(REQUEST_CHECK_ERROR, bundle.getString("problem_in_db"));
            doGet(request, response);
        }
    }

    private void checkTwiceSend(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
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
