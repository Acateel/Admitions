package com.adminitions.admitions.admin;

import com.adminitions.admitions.finalizers.BasicFinalizer;
import com.adminitions.admitions.finalizers.Finalizer;
import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "FinalizerServlet", value = "/Finalizer")
public class FinalizerServlet extends HttpServlet {
    private boolean called = false;

    protected transient Finalizer finalizer;

    @Override
    public void init() throws ServletException {
        RequestDao requestDao = (RequestDao) getServletContext().getAttribute("RequestDao");
        FacultyDao facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
        finalizer = new BasicFinalizer(facultyDao, requestDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!called) {
            try {
                finalizer.finalizeRequests();
                called = true;
                response.sendRedirect("index.jsp");
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
