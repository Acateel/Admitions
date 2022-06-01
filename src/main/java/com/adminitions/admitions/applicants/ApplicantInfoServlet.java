package com.adminitions.admitions.applicants;

import com.adminitions.data_access.ApplicantDao;
import com.adminitions.data_access.DaoException;
import com.adminitions.entities.Applicant;
import com.adminitions.entities.users.Role;
import com.adminitions.entities.users.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ApplicantInfoServlet", value = "/ApplicantInfo")
public class ApplicantInfoServlet extends HttpServlet {
    protected transient ApplicantDao applicantDao;

    @Override
    public void init() throws ServletException {
        applicantDao = (ApplicantDao) getServletContext().getAttribute("ApplicantDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("User");
        if (user == null || user.getRole() != Role.APPLICANT) {
            response.sendRedirect("WEB-INF/error_page/accessDenied.jsp");
        } else {
            Applicant applicant = null;
            try {
                applicant = applicantDao.findEntityById(user.getApplicantId());
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("applicant", applicant);
            request.getRequestDispatcher("WEB-INF/applicants/applicantInfo.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
