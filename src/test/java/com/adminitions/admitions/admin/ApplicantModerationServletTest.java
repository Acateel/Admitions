package com.adminitions.admitions.admin;

import com.adminitions.data_access.ApplicantDao;
import com.adminitions.data_access.DaoException;
import com.adminitions.entities.Applicant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicantModerationServletTest {

    ApplicantModerationServlet servlet;
    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher dispatcher;

    ApplicantDao applicantDao;

    Applicant applicant;
    @BeforeEach
    void setUp() throws DaoException {
        servlet = new ApplicantModerationServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("WEB-INF/admin_panels/applicant_moderation.jsp")).thenReturn(dispatcher);

        applicantDao = mock(ApplicantDao.class);
        servlet.applicantDao = applicantDao;
        when(applicantDao.findAll()).thenReturn(new ArrayList<>());

        applicant = new Applicant();
        when(request.getParameter("applicant_id")).thenReturn("1");
        when(applicantDao.findEntityById(1)).thenReturn(applicant);
    }

    @Test
    void doGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(dispatcher).forward(request,response);
    }

    @Test
    void doPost_block() throws ServletException, IOException, DaoException {
        when(request.getParameter("type")).thenReturn("block");
        applicant.setBlock(true);

        servlet.doPost(request, response);

        verify(applicantDao).findEntityById(1);
        verify(applicantDao).update(applicant);
    }

    @Test
    void doPost_deblock() throws ServletException, IOException, DaoException {
        when(request.getParameter("type")).thenReturn("deblock");

        servlet.doPost(request, response);

        verify(applicantDao).findEntityById(1);
        verify(applicantDao).update(applicant);
    }

    @Test
    void doPost_default() throws ServletException, IOException, DaoException {
        when(request.getParameter("type")).thenReturn("");

        servlet.doPost(request, response);

        verify(applicantDao, never()).findEntityById(1);
        verify(applicantDao, never()).update(applicant);
    }
}