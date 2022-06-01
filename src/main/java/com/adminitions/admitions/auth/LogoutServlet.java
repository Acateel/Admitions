package com.adminitions.admitions.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "/LogoutServlet", value = "/Logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LogoutServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Logout from session");
        // remove User and Applicant full name from session
        HttpSession session = request.getSession();
        session.removeAttribute("User");
        session.removeAttribute("Name");
        response.sendRedirect("index.jsp");
    }
}
