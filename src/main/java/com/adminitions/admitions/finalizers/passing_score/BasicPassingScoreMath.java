package com.adminitions.admitions.finalizers.passing_score;

import com.adminitions.entities.request.Request;
import com.adminitions.entities.request.RequestStatus;

public class BasicPassingScoreMath implements PassingScoreMathable {

    @Override
    public void setPassing(Request request) {
        if (request.getRatingScore() < 100) {
            request.setStatus(RequestStatus.NOT_ALLOWED);
        } else {
            request.setStatus(RequestStatus.ALLOWED);
        }
    }
}
