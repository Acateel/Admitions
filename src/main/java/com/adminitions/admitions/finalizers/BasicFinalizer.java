package com.adminitions.admitions.finalizers;

import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;
import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import com.adminitions.entities.Faculty;
import com.adminitions.entities.request.Request;
import com.adminitions.entities.request.RequestStatus;

import java.sql.SQLException;
import java.util.List;

public class BasicFinalizer extends Finalizer{
    public static void main(String[] args) throws SQLException, DaoException {
        BasicConnectionPool pool = BasicConnectionPool.create(
                "jdbc:mysql://localhost:3306/admissions",
                "root",
                "pass"
        );
        RequestDao requestDao = new RequestDao(pool);
        BasicFinalizer finalizer = new BasicFinalizer(new FacultyDao(pool), requestDao);
        finalizer.finalize();

        for(Request request: requestDao.findAllByApplicantId(1, RequestStatus.ALLOWED)){
            System.out.println(request);
        }
    }
    public BasicFinalizer(FacultyDao facultyDao, RequestDao requestDao) {
        super(facultyDao, requestDao);
    }

    public void finalize() throws DaoException {
        List<Faculty> faculties = facultyDao.findAll();
        precessed(faculties);
        setBudget(faculties);
    }

    private void setBudget(List<Faculty> faculties) throws DaoException {
        for(Faculty faculty : faculties){
            List<Request> requests = requestDao.findAllWitStatus(faculty.getId(), RequestStatus.ALLOWED);
            int count = 0;
            for(Request request : requests){
                if(count < faculty.getBudgetSeats()){
                    request.setStatus(RequestStatus.BUDGET);
                    requestDao.update(request);
                    setStatusToOtherRequests(request, RequestStatus.RECOMMEND_BUDGET);
                }
                else{
                    break;
                }
                count++;
            }
        }
    }

    private void setStatusToOtherRequests(Request request, RequestStatus status) throws DaoException {
        List<Request> otherRequests = requestDao.findAllByApplicantId(request.getApplicantId(), RequestStatus.ALLOWED);
        for(Request otherRequest : otherRequests){
            otherRequest.setStatus(status);
            requestDao.update(otherRequest);
        }
    }

    private void precessed(List<Faculty> faculties) throws DaoException {
        for(Faculty faculty : faculties){
            List<Request> requests = requestDao.findAllWithFaculty(faculty.getId());
            for(Request request : requests){
                if(request.getStatus() == RequestStatus.NOT_PROCESSED){
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
