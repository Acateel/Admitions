package com.adminitions.admitions.auth;

import com.adminitions.entities.Applicant;
import com.adminitions.entities.users.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Struct;

@WebServlet(name = "RegistrationServlet", value = "/Registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        checkEmail(request, response, email);
        String password = request.getParameter("psw");
        checkPassword(request, response, password);
        String repeatPassword = request.getParameter("psw-repeat");
        checkRepeatPassword(request, response, password, repeatPassword);

        //initialise
        User user = new User();
        user.setLogin(email);
        user.setPassword(password);

        Applicant applicant = new Applicant();
        applicant.setEmail(email);

        writeApplicant(response, applicant, user);
    }

    private static final String REGEX_EMAIL = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";

    private void checkEmail(HttpServletRequest request, HttpServletResponse response, String email) throws ServletException, IOException {
        if (!email.matches(REGEX_EMAIL)) {
            request.setAttribute("EmailError", "Email is not format");
            doGet(request, response);
        }
    }

    private static final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    private void checkPassword(HttpServletRequest request, HttpServletResponse response, String password) throws ServletException, IOException {
        if (!password.matches(REGEX_PASSWORD)) {
            request.setAttribute("PasswordError", "Minimum eight characters, at least one letter and one number");
            doGet(request, response);
        }
    }

    private void checkRepeatPassword(HttpServletRequest request, HttpServletResponse response, String password, String passwordRepeat) throws ServletException, IOException {
        if (!password.equals(passwordRepeat)) {
            request.setAttribute("PasswordRepeatError", "Passwords isn`t one the same");
            doGet(request, response);
        }
    }

    private void writeApplicant(HttpServletResponse response, Applicant applicant, User user) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<pre>" + applicant.toString() + "<pre>");
        out.println("<pre>" + user.toString() + "<pre>");
        out.println("</body></html>");
        out.close();
    }


}
