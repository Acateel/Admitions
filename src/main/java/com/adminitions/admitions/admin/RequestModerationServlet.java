package com.adminitions.admitions.admin;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;
import com.adminitions.entities.request.Request;
import com.adminitions.entities.request.RequestStatus;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "RequestModerationServlet", value = "/RequestModeration")
public class RequestModerationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RequestModerationServlet.class);

    protected transient FacultyDao facultyDao;
    protected transient RequestDao requestDao;
    private transient int facultiesId;

    @Override
    public void init() throws ServletException {
        logger.info("Request moderation servlet init");
        // init dao classes
        requestDao = (RequestDao) getServletContext().getAttribute("RequestDao");
        facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Request moderation servlet GET method");
        try {
            // take faculty id from parameters
            if (request.getParameter("faculty_id") != null) {
                // if faculty id exist in parameters
                facultiesId = Integer.parseInt(request.getParameter("faculty_id"));
            } else if (facultiesId == 0) {
                response.sendRedirect("FacultyModeration");
            }
            // send data in request moderation page
            request.setAttribute("faculty", facultyDao.findEntityById(facultiesId));
            request.getSession().setAttribute("faculty", facultyDao.findEntityById(facultiesId));
            request.setAttribute("requests", requestDao.findAllWithFaculty(facultiesId));
            // redirect to request moderation page
            request.getRequestDispatcher("WEB-INF/admin_panels/request_moderation.jsp").forward(request, response);
        } catch (DaoException e) {
            logger.error("Request moderation servlet DaoException");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Request moderation servlet POST method");
        // take applicant id from parameter
        int applicantId = Integer.parseInt(request.getParameter("applicant_id"));
        try {
            // change request status
            Request sendRequest = requestDao.findRequestByIds(facultiesId, applicantId);
            if(sendRequest.getStatus()==RequestStatus.BUDGET){
                sendRequest.setStatus(RequestStatus.NOT_PROCESSED);
            }
            else{
                sendRequest.setStatus(RequestStatus.BUDGET);
            }
            // update request
            requestDao.update(sendRequest);
        } catch (DaoException e) {
            logger.error("Request moderation servlet DaoException");
            doGet(request, response);
        }
        // redirect to request moderation page
        doGet(request, response);
    }
}
