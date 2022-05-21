package com.adminitions.admitions.faculty_servlets;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "FacultyServlet", value = "/FacultyServlet")
public class FacultyServlet extends HttpServlet {
    FacultyDao facultyDao;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            BasicConnectionPool pool = BasicConnectionPool.create(
                    "jdbc:mysql://localhost:3306/admissions",
                    "root",
                    "pass"
            );
            facultyDao = new FacultyDao(pool);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("faculties", facultyDao.findAll());
            request.getRequestDispatcher("/WEB-INF/faculty/show_faculties.jsp").forward(request, response);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
