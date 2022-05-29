package com.adminitions.admitions.finalizers;

import com.adminitions.admitions.finalizers.passing_score.BasicPassingScoreMath;
import com.adminitions.admitions.finalizers.passing_score.PassingScoreMathable;
import com.adminitions.admitions.finalizers.rating_score.BasicRattingScoreMath;
import com.adminitions.admitions.finalizers.rating_score.RatingScoreMathable;
import com.adminitions.data_access.DaoException;
import com.adminitions.data_access.FacultyDao;
import com.adminitions.data_access.RequestDao;

public abstract class Finalizer {
    FacultyDao facultyDao;
    RequestDao requestDao;
    RatingScoreMathable ratingScoreMath;
    PassingScoreMathable passingScoreMath;

    protected Finalizer(FacultyDao facultyDao, RequestDao requestDao) {
        this.facultyDao = facultyDao;
        this.requestDao = requestDao;
        ratingScoreMath = new BasicRattingScoreMath();
        passingScoreMath = new BasicPassingScoreMath();
    }

    public abstract void finalizeRequests() throws DaoException;

    public void setRatingScoreMath(RatingScoreMathable ratingScoreMath) {
        this.ratingScoreMath = ratingScoreMath;
    }

    public void setPassingScoreMath(PassingScoreMathable passingScoreMath) {
        this.passingScoreMath = passingScoreMath;
    }
}
