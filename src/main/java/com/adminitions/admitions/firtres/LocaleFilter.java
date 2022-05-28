package com.adminitions.admitions.firtres;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(filterName = "LocaleFilter", urlPatterns = {"/*"})
public class LocaleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        langStartingInitialise(req.getSession().getAttribute("lang") == null, req, "");
        langStartingInitialise(req.getParameter("locale") != null, req, req.getParameter("locale"));

        chain.doFilter(request, response);
    }

    private void langStartingInitialise(boolean req, HttpServletRequest req1, String o) {
        if(req){
            req1.getSession().setAttribute("lang", o);
        }
    }
}
