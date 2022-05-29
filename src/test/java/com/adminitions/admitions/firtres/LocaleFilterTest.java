package com.adminitions.admitions.firtres;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocaleFilterTest {

    LocaleFilter filter;
    HttpServletRequest request;
    HttpServletResponse response;
    FilterChain chain;
    @BeforeEach
    void setUp() {
        filter = new LocaleFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
    }

    @Test
    void doFilter_null() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("lang")).thenReturn(null);

        filter.doFilter(request, response, chain);

        verify(session).setAttribute("lang", "");
    }
}