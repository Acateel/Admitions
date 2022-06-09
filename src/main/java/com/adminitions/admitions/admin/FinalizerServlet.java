package com.adminitions.admitions.admin;

import com.adminitions.admitions.finalizers.BasicFinalizer;
import com.adminitions.admitions.finalizers.Finalizer;
import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;
import com.adminitions.entities.users.Role;
import com.adminitions.entities.users.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "FinalizerServlet", value = "/Finalizer")
public class FinalizerServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(FinalizerServlet.class);
    private boolean called = false;

    protected transient Finalizer finalizer;

    @Override
    public void init() throws ServletException {
        logger.info("Finalizer servlet init");
        // init dao classes
        RequestDao requestDao = (RequestDao) getServletContext().getAttribute("RequestDao");
        FacultyDao facultyDao = (FacultyDao) getServletContext().getAttribute("FacultyDao");
        // init finalizer
        finalizer = new BasicFinalizer(facultyDao, requestDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Finalizer servlet GET method");
        User user = (User) request.getSession().getAttribute("User");
        if (user != null || user.getRole() == Role.ADMIN) {
            finalizeRequests(response);
        }
    }

    private void finalizeRequests(HttpServletResponse response) throws IOException {
        // called finalizer only once
        if (!called) {
            try {
                finalizer.finalizeRequests();
                called = true;
                response.sendRedirect("index.jsp");
            } catch (DaoException e) {
                logger.error("Finalizer servlet DAO exception");
            }
        }
    }
}
