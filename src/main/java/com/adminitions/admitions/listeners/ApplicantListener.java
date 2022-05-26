package com.adminitions.admitions.listeners;

import com.adminitions.data_access.ApplicantDao;
import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebListener
public class ApplicantListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    ApplicantDao applicantDao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        BasicConnectionPool pool = (BasicConnectionPool) sce.getServletContext().getAttribute("connectionPool");
        applicantDao = new ApplicantDao(pool);
        ServletContext context = sce.getServletContext();
        context.setAttribute("ApplicantDao", applicantDao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
        applicantDao = null;
    }
}
