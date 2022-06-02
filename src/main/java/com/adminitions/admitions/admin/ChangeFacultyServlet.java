package com.adminitions.admitions.admin;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.entities.Faculty;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "ChangeFacultyServlet", value = "/ChangeFaculty")
public class ChangeFacultyServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ChangeFacultyServlet.class);
    protected transient FacultyDao facultyDao;
    protected transient int facultyId;

    @Override
    public void init() throws ServletException {
        logger.info("Change faculty servlet init");
        // init dao class
        facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Change faculty servlet GET method");
        // take faculty id form request parameter
        facultyId = Integer.parseInt(request.getParameter("faculty_id"));
        try {
            // find faculty by id and add ist in request attribute
            Faculty faculty = facultyDao.findEntityById(facultyId);
            request.setAttribute("faculty", faculty);
        } catch (DaoException e) {
            // if faculty with id not found
            logger.error("Change Faculty servlet DAO exception");
        }
        // redirect to change faculty page
        request.getRequestDispatcher("WEB-INF/admin_panels/change_faculty.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Change faculty servlet POST method");
        try {
            // take faculty data from parameters
            String name = request.getParameter("faculty_name");
            int budgetSeats = Integer.parseInt(request.getParameter("budget_seats"));
            int totalSeats = Integer.parseInt(request.getParameter("total_seats"));
            // update faculty
            updateFaculty(name, budgetSeats, totalSeats);
            // redirect to faculty moderation page
            response.sendRedirect("FacultyModeration");
        }
        catch (DaoException exception){
            logger.error("Change Faculty servlet DAO exception");
            response.sendRedirect("FacultyModeration");
        }
        catch (NumberFormatException exception){
            logger.warn("Change Faculty servlet data not format");
            doGet(request, response);
        }
    }

    private void updateFaculty(String name, int budgetSeats, int totalSeats) throws DaoException {
        Faculty faculty = facultyDao.findEntityById(facultyId);
        faculty.setName(name);
        faculty.setBudgetSeats(budgetSeats);
        faculty.setTotalSeats(totalSeats);
        facultyDao.update(faculty);
    }
}
