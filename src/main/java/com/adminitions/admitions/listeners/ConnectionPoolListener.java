package com.adminitions.admitions.listeners;

import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.sql.SQLException;

@WebListener
public class ConnectionPoolListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
    BasicConnectionPool pool;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        try {
            Class.forName("com.mysql.jdbc.Driver");
            pool = BasicConnectionPool.create(
                    "jdbc:mysql://localhost:3306/admissions",
                    "root",
                    "pass"
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
            // add log
        }
        ServletContext context = sce.getServletContext();
        context.setAttribute("connectionPool", pool);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
        pool = null;
    }
}
