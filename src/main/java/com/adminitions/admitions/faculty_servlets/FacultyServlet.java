package com.adminitions.admitions.faculty_servlets;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.entities.Faculty;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "FacultyServlet", value = "/Faculty")
public class FacultyServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(FacultyServlet.class);
    protected transient FacultyDao facultyDao;

    @Override
    public void init() throws ServletException {
        logger.info("Faculty servlet init");
        // init dao class
        facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Faculty servlet GET method");
        try {
            // add faculties in request attribute and redirect show faculties page
            request.setAttribute("faculties", facultyDao.findAll());
            request.getRequestDispatcher("/WEB-INF/faculty/show_faculties.jsp").forward(request, response);
        } catch (DaoException e) {
            logger.info("Faculty servlet DaoException");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Faculty servlet POST method");
        try {
            // get order from request parameter
            String order = request.getParameter("order");
            // select faculties by order
            List<Faculty> faculties = selectFaculties(order);
            // add faculties in request attribute and redirect show faculties page
            request.setAttribute("faculties", faculties);
            request.getRequestDispatcher("/WEB-INF/faculty/show_faculties.jsp").forward(request, response);
        } catch (DaoException e) {
            logger.info("Faculty servlet DaoException");
        }
    }

    private List<Faculty> selectFaculties(String order) throws DaoException {
        List<Faculty> faculties;
        // set faculties list by order
        switch (order){
            case "byId":
                faculties = facultyDao.findAll();
                break;
            case "byName":
                faculties = facultyDao.findAllByName();
                break;
            case "byNameRevers":
                faculties = facultyDao.findAllByNameRevers();
                break;
            case "byBudget":
                faculties = facultyDao.findAllByBudget();
                break;
            case "byTotal":
                faculties = facultyDao.findAllByTotal();
                break;
            default:
                logger.warn("Faculty servlet wrong order");
                faculties = facultyDao.findAll();
                break;
        }
        return faculties;
    }
}
