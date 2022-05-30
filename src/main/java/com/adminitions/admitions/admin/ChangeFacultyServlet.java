package com.adminitions.admitions.admin;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.entities.Faculty;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ChangeFacultyServlet", value = "/ChangeFaculty")
public class ChangeFacultyServlet extends HttpServlet {
    protected transient FacultyDao facultyDao;
    protected transient int facultyId;

    @Override
    public void init() throws ServletException {
        facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        facultyId = Integer.parseInt(request.getParameter("faculty_id"));
        try {
            Faculty faculty = facultyDao.findEntityById(facultyId);
            request.setAttribute("faculty", faculty);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("WEB-INF/admin_panels/change_faculty.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String name = request.getParameter("faculty_name");
            int budgetSeats = Integer.parseInt(request.getParameter("budget_seats"));
            int totalSeats = Integer.parseInt(request.getParameter("total_seats"));
            updateFaculty(name, budgetSeats, totalSeats);
            response.sendRedirect("FacultyModeration");
        }
        catch (DaoException exception){
            response.sendRedirect("FacultyModeration");
        }
        catch (NumberFormatException exception){
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
