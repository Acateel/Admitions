package com.adminitions.admitions.finalizers;

import com.adminitions.admitions.finalizers.passing_score.BasicPassingScoreMath;
import com.adminitions.admitions.finalizers.passing_score.PassingScoreMathable;
import com.adminitions.admitions.finalizers.rating_score.BasicRattingScoreMath;
import com.adminitions.admitions.finalizers.rating_score.RatingScoreMathable;
import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;
import com.adminitions.entities.Faculty;
import com.adminitions.entities.request.Request;
import com.adminitions.entities.request.RequestStatus;
import com.mysql.cj.LicenseConfiguration;

import java.util.ArrayList;
import java.util.List;

public class BasicFinalizer extends Finalizer{
    FacultyDao facultyDao;
    RequestDao requestDao;
    RatingScoreMathable ratingScoreMath;
    PassingScoreMathable passingScoreMath;

    public BasicFinalizer(FacultyDao facultyDao, RequestDao requestDao) {
        this.facultyDao = facultyDao;
        this.requestDao = requestDao;
        ratingScoreMath = new BasicRattingScoreMath();
        passingScoreMath = new BasicPassingScoreMath();
    }

    public void finalize() throws DaoException {
        List<Faculty> faculties = facultyDao.findAll();
        precessed(faculties);

    }

    public List<Request> getAllowedRequests(int facultyId) throws DaoException {
        List<Request> requests = requestDao.findAllWithFaculty(facultyId);
        List<Request> allowedRequests = new ArrayList<>();
        for(Request request: requests){
            if(request.getStatus() == RequestStatus.ALLOWED){
                allowedRequests.add(request);
            }
        }
        return allowedRequests;
    }
    private void precessed(List<Faculty> faculties) throws DaoException {
        for(Faculty faculty : faculties){
            List<Request> requests = requestDao.findAllWithFaculty(faculty.getId());
            for(Request request : requests){
                if(request.getStatus() == RequestStatus.NOT_PROCESSED){
                    int ratting = ratingScoreMath.setRattingScore(request);
                    request.setRatingScore(ratting);
                    ratingScoreMath.setRattingScore(request);
                    requestDao.update(request);
                }
            }
        }
    }

    public void setRatingScoreMath(RatingScoreMathable ratingScoreMath) {
        this.ratingScoreMath = ratingScoreMath;
    }

    public void setPassingScoreMath(PassingScoreMathable passingScoreMath) {
        this.passingScoreMath = passingScoreMath;
    }
}
