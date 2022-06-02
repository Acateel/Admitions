package com.adminitions.admitions.admin;

import com.adminitions.data_access.ApplicantDao;
import com.adminitions.data_access.DaoException;
import com.adminitions.entities.Applicant;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "ApplicantModerationServlet", value = "/ApplicantModeration")
public class ApplicantModerationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ApplicantModerationServlet.class);
    protected transient ApplicantDao applicantDao;

    @Override
    public void init() throws ServletException {
        logger.info("Applicant moderation servlet init");
        applicantDao = (ApplicantDao) getServletContext().getAttribute("ApplicantDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Applicant moderation servlet GET method");
        // redirect to applicant moderation page with applicants list
        try {
            request.setAttribute("applicants", applicantDao.findAll());
            request.getRequestDispatcher("WEB-INF/admin_panels/applicant_moderation.jsp").forward(request, response);
        } catch (DaoException e) {
            logger.error("Applicant moderation servlet DaoException");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Applicant moderation servlet POST method");
        String type = request.getParameter("type");
        switch (type){
            case "block": // if press block button
                setBlock(request, response, true);
                break;
            case "deblock": // if press deblock button
                setBlock(request, response, false);
                break;
            default: // uncorrected press button
                logger.warn("Applicant moderation servlet wrong type for post method");
                doGet(request,response);
                break;
        }
        // redirect to applicant moderation page
        doGet(request,response);
    }

    private void setBlock(HttpServletRequest request, HttpServletResponse response, boolean block) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("applicant_id"));
        try {
            // change block and update its
            Applicant applicant = applicantDao.findEntityById(id);
            applicant.setBlock(block);
            applicantDao.update(applicant);
        } catch (DaoException e) {
            logger.error("Applicant moderation servlet DaoException");
            // redirect to applicant moderation page
            doGet(request, response);
        }
    }
}
