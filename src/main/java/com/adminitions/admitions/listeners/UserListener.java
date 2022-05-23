package com.adminitions.admitions.listeners;

import com.adminitions.data_access.UserDao;
import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebListener
public class UserListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    UserDao userDao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        BasicConnectionPool pool = (BasicConnectionPool) sce.getServletContext().getAttribute("connectionPool");
        userDao = new UserDao(pool);
        ServletContext context = sce.getServletContext();
        context.setAttribute("UserDao", userDao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
        userDao = null;
    }
}
