package com.adminitions.admitions.applicants;

import com.adminitions.data_access.ApplicantDao;
import com.adminitions.data_access.DaoException;
import com.adminitions.entities.Applicant;
import com.adminitions.entities.users.Role;
import com.adminitions.entities.users.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "ApplicantInfoServlet", value = "/ApplicantInfo")
@MultipartConfig
public class ApplicantInfoServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ApplicantInfoServlet.class);
    protected transient ApplicantDao applicantDao;

    @Override
    public void init() throws ServletException {
        logger.info("Initialise Applicant info servlet");
        // initialise dao class
        applicantDao = (ApplicantDao) getServletContext().getAttribute("ApplicantDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Applicant info GET method");
        // get user from session
        User user = (User) request.getSession().getAttribute("User");
        // if user not found or his not applicant redirect to access denied page
        if (user == null || user.getRole() != Role.APPLICANT) {
            request.getRequestDispatcher("WEB-INF/error_page/accessDenied.jsp").forward(request, response);
        } else {
            // get applicant form DB
            Applicant applicant = null;
            try {
                applicant = applicantDao.findEntityById(user.getApplicantId());
            } catch (DaoException e) {
                logger.error("DaoException");
            }
            // add applicant in request attribute
            request.setAttribute("applicant", applicant);
            // redirects to applicant info page
            request.getRequestDispatcher("WEB-INF/applicants/applicantInfo.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Applicant info POST method");
        // get user from session
        User user = (User) request.getSession().getAttribute("User");
        // if user not found or his not applicant redirect to access denied page
        if (user == null || user.getRole() != Role.APPLICANT) {
            request.getRequestDispatcher("WEB-INF/error_page/accessDenied.jsp").forward(request, response);
        } else {
            // add scan in DB
            int applicantId = user.getApplicantId();
            Part part = request.getPart("file");
            try {
                applicantDao.updateAttestation(applicantId, part.getInputStream());
            } catch (DaoException e) {
                logger.error("Update attestation DaoException");
            }
        }
        // redirect to status info page
        doGet(request, response);
    }
}
