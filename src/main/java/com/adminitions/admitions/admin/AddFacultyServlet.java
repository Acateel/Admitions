package com.adminitions.admitions.admin;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.entities.Faculty;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AddFacultyServlet", value = "/AddFaculty")
public class AddFacultyServlet extends HttpServlet {
    private transient FacultyDao facultyDao;

    @Override
    public void init() throws ServletException {
        facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/admin_panels/add_faculty.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String name = request.getParameter("faculty_name");
            int budgetSeats = Integer.parseInt(request.getParameter("budget_seats"));
            int totalSeats = Integer.parseInt(request.getParameter("total_seats"));
            addFaculty(name, budgetSeats, totalSeats);
            response.sendRedirect("FacultyModeration");
        }
        catch (DaoException exception){
            response.sendRedirect("FacultyModeration");
        }
        catch (NumberFormatException exception){
            doGet(request, response);
        }
    }

    private void addFaculty(String name, int budgetSeats, int totalSeats) throws DaoException {
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setBudgetSeats(budgetSeats);
        faculty.setTotalSeats(totalSeats);
        facultyDao.create(faculty);
    }
}
