package com.adminitions.admitions.admin;

import com.adminitions.data_access.ApplicantDao;
import com.adminitions.data_access.DaoException;
import com.adminitions.entities.Applicant;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ApplicantModerationServlet", value = "/ApplicantModeration")
public class ApplicantModerationServlet extends HttpServlet {
    protected transient ApplicantDao applicantDao;

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
        String type = request.getParameter("type");
        switch (type){
            case "block":
                setBlock(request, response, true);
                break;
            case "deblock":
                setBlock(request, response, false);
                break;
            default:
                doGet(request,response);
                break;
        }
        doGet(request,response);
    }

    private void setBlock(HttpServletRequest request, HttpServletResponse response, boolean block) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("applicant_id"));
        try {
            Applicant applicant = applicantDao.findEntityById(id);
            applicant.setBlock(block);
            applicantDao.update(applicant);
        } catch (DaoException e) {
            // add log
            doGet(request, response);
        }
    }
}
