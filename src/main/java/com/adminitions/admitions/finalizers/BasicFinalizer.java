package com.adminitions.admitions.finalizers;

import com.adminitions.admitions.finalizers.passing_score.BasicPassingScoreMath;
import com.adminitions.admitions.finalizers.passing_score.PassingScoreMathable;
import com.adminitions.admitions.finalizers.rating_score.BasicRattingScoreMath;
import com.adminitions.admitions.finalizers.rating_score.RatingScoreMathable;
import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;
import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import com.adminitions.entities.Faculty;
import com.adminitions.entities.request.Request;
import com.adminitions.entities.request.RequestStatus;
import com.mysql.cj.LicenseConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasicFinalizer extends Finalizer{
    public static void main(String[] args) throws SQLException, DaoException {
        BasicConnectionPool pool = BasicConnectionPool.create(
                "jdbc:mysql://localhost:3306/admissions",
                "root",
                "pass"
        );
        BasicFinalizer finalizer = new BasicFinalizer(new FacultyDao(pool), new RequestDao(pool));
        finalizer.finalize();
    }
    public BasicFinalizer(FacultyDao facultyDao, RequestDao requestDao) {
        super(facultyDao, requestDao);
    }

    public void finalize() throws DaoException {
        List<Faculty> faculties = facultyDao.findAll();
        precessed(faculties);
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
