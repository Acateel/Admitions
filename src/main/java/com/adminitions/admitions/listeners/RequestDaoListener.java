package com.adminitions.admitions.listeners;

import com.adminitions.data_access.RequestDao;
import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebListener
public class RequestDaoListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    RequestDao requestDao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        BasicConnectionPool pool = (BasicConnectionPool) sce.getServletContext().getAttribute("connectionPool");
        requestDao = new RequestDao(pool);
        ServletContext context = sce.getServletContext();
        context.setAttribute("RequestDao", requestDao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
        requestDao = null;
    }
}
