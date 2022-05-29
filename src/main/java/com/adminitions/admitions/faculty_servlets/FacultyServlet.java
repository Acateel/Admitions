package com.adminitions.admitions.faculty_servlets;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.entities.Faculty;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "FacultyServlet", value = "/Faculty")
public class FacultyServlet extends HttpServlet {
    protected transient FacultyDao facultyDao;

    @Override
    public void init() throws ServletException {
        facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("faculties", facultyDao.findAll());
            request.getRequestDispatcher("/WEB-INF/faculty/show_faculties.jsp").forward(request, response);
        } catch (DaoException e) {
            throw new RuntimeException(e);
            // add log and error page
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String order = request.getParameter("order");
            List<Faculty> faculties = selectFaculties(order);
            request.setAttribute("faculties", faculties);
            request.getRequestDispatcher("/WEB-INF/faculty/show_faculties.jsp").forward(request, response);
        } catch (DaoException e) {
            throw new RuntimeException(e);
            // add log and error page
        }
    }

    private List<Faculty> selectFaculties(String order) throws DaoException {
        List<Faculty> faculties;
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
                faculties = facultyDao.findAll();
                break;
        }
        return faculties;
    }
}
