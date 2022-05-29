package com.adminitions.admitions.finalizers;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;
import com.adminitions.entities.Faculty;
import com.adminitions.entities.request.Request;
import com.adminitions.entities.request.RequestStatus;

import java.util.List;

public class BasicFinalizer extends Finalizer {

    public BasicFinalizer(FacultyDao facultyDao, RequestDao requestDao) {
        super(facultyDao, requestDao);
    }

    @Override
    public void finalizeRequests() throws DaoException {
        List<Faculty> faculties = facultyDao.findAll();
        precessed(faculties);
        setBudget(faculties);
        seContract(faculties);
    }

    private void setBudget(List<Faculty> faculties) throws DaoException {
        for (Faculty faculty : faculties) {
            List<Request> requests = requestDao.findAllWitStatus(faculty.getId(), RequestStatus.ALLOWED);
            int count = 0;
            int seats = faculty.getBudgetSeats() - requestDao.findAllWitStatus(faculty.getId(), RequestStatus.BUDGET).size();
            for (Request request : requests) {
                if (count < seats) {
                    request.setStatus(RequestStatus.BUDGET);
                    requestDao.update(request);
                    setStatusToOtherRequests(request, RequestStatus.RECOMMEND_BUDGET);
                } else {
                    break;
                }
                count++;
            }
        }
    }

    private void seContract(List<Faculty> faculties) throws DaoException {
        for (Faculty faculty : faculties) {
            List<Request> requests = requestDao.findAllWitStatus(faculty.getId(), RequestStatus.ALLOWED);
            int count = 0;
            int seats = faculty.getTotalSeats() - faculty.getBudgetSeats() - requestDao.findAllWitStatus(faculty.getId(), RequestStatus.CONTRACT).size();
            for (Request request : requests) {
                if (count < seats) {
                    request.setStatus(RequestStatus.CONTRACT);
                    requestDao.update(request);
                    setStatusToOtherRequests(request, RequestStatus.RECOMMEND_CONTRACT);
                } else {
                    break;
                }
                count++;
            }
        }
    }

    private void setStatusToOtherRequests(Request request, RequestStatus status) throws DaoException {
        List<Request> otherRequests = requestDao.findAllByApplicantId(request.getApplicantId(), RequestStatus.ALLOWED);
        for (Request otherRequest : otherRequests) {
            otherRequest.setStatus(status);
            requestDao.update(otherRequest);
        }
    }

    private void precessed(List<Faculty> faculties) throws DaoException {
        for (Faculty faculty : faculties) {
            List<Request> requests = requestDao.findAllWithFaculty(faculty.getId());
            for (Request request : requests) {
                if (request.getStatus() == RequestStatus.NOT_PROCESSED) {
                    int ratting = ratingScoreMath.setRattingScore(request);
                    request.setRatingScore(ratting);
                    ratingScoreMath.setRattingScore(request);
                    passingScoreMath.setPassing(request);
                    requestDao.update(request);
                }
            }
        }
    }

}
