package com.adminitions.admitions.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogoutServletTest {

    @Test
    void doGet() throws ServletException, IOException {
        LogoutServlet logoutServlet = new LogoutServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        logoutServlet.doGet(request, response);

        verify(request, times(1)).getSession();
        verify(session).removeAttribute("User");
        verify(session).removeAttribute("Name");
        verify(response).sendRedirect("index.jsp");
    }
}