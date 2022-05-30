package com.adminitions.admitions.admin;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;
import com.adminitions.entities.request.Request;
import com.adminitions.entities.request.RequestStatus;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RequestModerationServlet", value = "/RequestModeration")
public class RequestModerationServlet extends HttpServlet {

    protected transient FacultyDao facultyDao;
    protected transient RequestDao requestDao;
    private transient int facultiesId;

    @Override
    public void init() throws ServletException {
        requestDao = (RequestDao) getServletContext().getAttribute("RequestDao");
        facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getParameter("faculty_id") != null) {
                facultiesId = Integer.parseInt(request.getParameter("faculty_id"));
            } else if (facultiesId == 0) {
                response.sendRedirect("FacultyModeration");
            }
            request.setAttribute("faculty", facultyDao.findEntityById(facultiesId));
            request.getSession().setAttribute("faculty", facultyDao.findEntityById(facultiesId));
            request.setAttribute("requests", requestDao.findAllWithFaculty(facultiesId));
            request.getRequestDispatcher("WEB-INF/admin_panels/request_moderation.jsp").forward(request, response);
        } catch (DaoException e) {
            throw new RuntimeException(e);
            // add log and error page
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int applicantId = Integer.parseInt(request.getParameter("applicant_id"));
        try {
            Request sendRequest = requestDao.findRequestByIds(facultiesId, applicantId);
            if(sendRequest.getStatus()==RequestStatus.BUDGET){
                sendRequest.setStatus(RequestStatus.NOT_PROCESSED);
            }
            else{
                sendRequest.setStatus(RequestStatus.BUDGET);
            }
            requestDao.update(sendRequest);
        } catch (DaoException e) {
            doGet(request, response);
        }
        doGet(request, response);
    }
}
