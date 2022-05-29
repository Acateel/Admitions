package com.adminitions.admitions.request_servlets;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.RequestDao;
import com.adminitions.entities.Faculty;
import com.adminitions.entities.request.Request;
import com.adminitions.entities.request.RequestStatus;
import com.adminitions.entities.users.User;
import com.adminitions.validators.Validator;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet(name = "SendRequestServlet", value = "/SendRequest")
public class SendRequestServlet extends HttpServlet {
    protected transient ResourceBundle bundle;
    protected transient RequestDao requestDao;
    protected static final String BUNDLE_SCORE_NOT_FORMAT = "score_not_format";
    protected static final String BUNDLE_SEND_STATUS_KEY = "SendRequestStatus";

    @Override
    public void init() throws ServletException {
        requestDao = (RequestDao) getServletContext().getAttribute("RequestDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/requests/send_request.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        bundle = getResourceBundle(request);

        Request sendRequest = getSendRequest(request, response);
        addRequestToDb(request, sendRequest);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private void addRequestToDb(HttpServletRequest request, Request sendRequest) {
        try{
            if(!requestDao.requestExist(sendRequest.getFacultiesId(), sendRequest.getApplicantId())){
                requestDao.create(sendRequest);
                request.setAttribute(BUNDLE_SEND_STATUS_KEY, bundle.getString("send_request_success"));
            }
            else{
                request.setAttribute(BUNDLE_SEND_STATUS_KEY, bundle.getString("send_request_not_success"));
            }
        } catch (DaoException e) {
            request.setAttribute(BUNDLE_SEND_STATUS_KEY, bundle.getString("send_request_db_problem"));
        }
    }

    private Request getSendRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int facultiesId = ((Faculty)request.getSession().getAttribute("faculty")).getId();
        int applicantId = ((User)request.getSession().getAttribute("User")).getApplicantId();
        Request sendRequest = new Request();
        sendRequest.setStatus(RequestStatus.NOT_PROCESSED);
        sendRequest.setFacultiesId(facultiesId);
        sendRequest.setApplicantId(applicantId);
        sendRequest.setMainSubject(getMainSubjectScore(request, response));
        sendRequest.setSecondSubject(getSecondSubjectScore(request, response));
        sendRequest.setSubSubject(getSubSubjectScore(request, response));
        sendRequest.setRatingScore(0);
        sendRequest.setAverageAttestationScore(getAttestationScore(request, response));
        sendRequest.setPublishTime(new Time(new Date().getTime()));
        return sendRequest;
    }

    private int getMainSubjectScore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mainSubject = request.getParameter("main_subject");
        if(!Validator.checkScoreSubject(mainSubject)){
            request.setAttribute("MainSubjectError", bundle.getString(BUNDLE_SCORE_NOT_FORMAT));
            doGet(request, response);
        }
        return Integer.parseInt(mainSubject);
    }

    private int getSecondSubjectScore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String secondSubject = request.getParameter("second_subject");
        if(!Validator.checkScoreSubject(secondSubject)){
            request.setAttribute("SecondSubjectError", bundle.getString(BUNDLE_SCORE_NOT_FORMAT));
            doGet(request, response);
        }
        return Integer.parseInt(secondSubject);
    }

    private int getSubSubjectScore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subSubject = request.getParameter("sub_subject");
        if(!Validator.checkScoreSubject(subSubject)){
            request.setAttribute("SubSubjectError", bundle.getString(BUNDLE_SCORE_NOT_FORMAT));
            doGet(request, response);
        }
        return Integer.parseInt(subSubject);
    }

    private float getAttestationScore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String averageAttestationScore = request.getParameter("average_attestation_score");
        averageAttestationScore = averageAttestationScore.replace(',', '.');
        if(!Validator.checkScoreAttestation(averageAttestationScore)){
            request.setAttribute("AverageScoreError", bundle.getString("score_attestation_not_format"));
            doGet(request, response);
        }
        return Float.parseFloat(averageAttestationScore);
    }

    private ResourceBundle getResourceBundle(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("lang");
        if(locale.length() > 0){
            String[] lamgs = locale.split("_");
            return ResourceBundle.getBundle("locales.content", new Locale(lamgs[0], lamgs[1]));
        }
        else{
            return ResourceBundle.getBundle("locales.content");
        }
    }
}
