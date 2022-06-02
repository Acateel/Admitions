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

@WebServlet(name = "AddFacultyServlet", value = "/AddFaculty")
public class AddFacultyServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AddFacultyServlet.class);
    protected transient FacultyDao facultyDao;

    @Override
    public void init() throws ServletException {
        logger.info("Add Faculty servlet init");
        // initialise dao class
        facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Add faculty servlet GET method");
        // redirects to add faculty form
        request.getRequestDispatcher("WEB-INF/admin_panels/add_faculty.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Add faculty servlet POST method");
        try {
            // take data from request parameters
            String name = request.getParameter("faculty_name");
            int budgetSeats = Integer.parseInt(request.getParameter("budget_seats"));
            int totalSeats = Integer.parseInt(request.getParameter("total_seats"));
            // add faculty with data into DB
            addFaculty(name, budgetSeats, totalSeats);
            //back to faculty moderation page
            response.sendRedirect("FacultyModeration");
        }
        catch (DaoException exception){
            logger.error("Add Faculty servlet Dao Exception");
            //redirect to faculty moderation page
            response.sendRedirect("FacultyModeration");
        }
        catch (NumberFormatException exception){
            logger.warn("Add Faculty servlet data not format");
            // redirect to add faculty form
            doGet(request, response);
        }
    }

    private void addFaculty(String name, int budgetSeats, int totalSeats) throws DaoException {
        // create faculty and add to DB
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setBudgetSeats(budgetSeats);
        faculty.setTotalSeats(totalSeats);
        facultyDao.create(faculty);
    }
}
