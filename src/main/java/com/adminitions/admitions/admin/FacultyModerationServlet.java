package com.adminitions.admitions.admin;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "FacultyModerationServlet", value = "/FacultyModeration")
public class FacultyModerationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(FacultyModerationServlet.class);
    protected transient FacultyDao facultyDao;

    @Override
    public void init() throws ServletException {
        logger.info("Faculty moderation servlet init");
        // init dao cass
        facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Faculty moderation servlet GET method");
        try {
            // get all faculty list and redirect to faculty moderation page
            request.setAttribute("faculties", facultyDao.findAll());
            request.getRequestDispatcher("WEB-INF/admin_panels/facultiest_moderation.jsp").forward(request, response);
        } catch (DaoException e) {
            logger.error("Faculty moderation servlet DaoException");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Faculty moderation servlet POST method");
        // get command type
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
                // wrong post method query
                logger.warn("Faculty moderation servlet wrong type in POST method");
                doGet(request, response);
                break;
        }
    }

    private void deleteFaculty(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // delete faculty by id if existing
        int facultyId = Integer.parseInt(request.getParameter("faculty_id"));
        try {
            facultyDao.delete(facultyId);
        } catch (DaoException e) {
            logger.error("Faculty moderation servlet DaoException");
        }
        doGet(request, response);
    }
}
