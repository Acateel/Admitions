package com.adminitions.admitions.admin;

import com.adminitions.admitions.finalizers.Finalizer;
import com.adminitions.data_access.DaoException;
import com.adminitions.entities.users.Role;
import com.adminitions.entities.users.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FinalizerServletTest {

    @Test
    void doGet() throws ServletException, IOException, DaoException {
        FinalizerServlet servlet = new FinalizerServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        User user = new User();
        user.setRole(Role.ADMIN);
        when(session.getAttribute("User")).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        Finalizer finalizer = mock(Finalizer.class);
        servlet.finalizer = finalizer;

        servlet.doGet(request,response);

        verify(finalizer).finalizeRequests();
        verify(response).sendRedirect("index.jsp");
    }
}