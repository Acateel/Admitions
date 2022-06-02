package com.adminitions.admitions.admin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "AdminMenuServlet", value = "/AdminMenu")
public class AdminMenuServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AdminMenuServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Admin menu servlet GET method");
        // redirect to admin menu page
        request.getRequestDispatcher("WEB-INF/admin_panels/adminMenu.jsp").forward(request, response);
    }
}
