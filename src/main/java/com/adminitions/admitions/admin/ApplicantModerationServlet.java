package com.adminitions.admitions.admin;

import com.adminitions.data_access.ApplicantDao;
import com.adminitions.data_access.DaoException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ApplicantModerationServlet", value = "/ApplicantModeration")
public class ApplicantModerationServlet extends HttpServlet {
    private transient ApplicantDao applicantDao;

    @Override
    public void init() throws ServletException {
        applicantDao = (ApplicantDao) getServletContext().getAttribute("ApplicantDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("applicants", applicantDao.findAll());
            request.getRequestDispatcher("WEB-INF/admin_panels/applicant_moderation.jsp").forward(request, response);
        } catch (DaoException e) {
            throw new RuntimeException(e);
            // add log and error page
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
