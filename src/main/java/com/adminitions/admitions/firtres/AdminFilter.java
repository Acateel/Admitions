package com.adminitions.admitions.firtres;

import com.adminitions.entities.users.Role;
import com.adminitions.entities.users.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "AdminFilter",
        urlPatterns = {
        "/AdminMenu",
        "/AddFaculty",
        "/ApplicantModeration",
        "/ChangeFaculty",
        "/FacultyModeration",
        "/Finalizer",
        "/RequestModeration"
})
public class AdminFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AdminFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        User user = (User) req.getSession().getAttribute("User");
        if(user == null || user.getRole() != Role.ADMIN){
            logger.warn("Access in Admin functions denied");
            req.getRequestDispatcher("WEB-INF/error_page/accessDenied.jsp").forward(request, response);
        }
        chain.doFilter(request, response);
    }
}
