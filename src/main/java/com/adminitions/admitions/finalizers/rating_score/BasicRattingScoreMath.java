package com.adminitions.admitions.finalizers.rating_score;

import com.adminitions.entities.request.Request;

public class BasicRattingScoreMath implements  RatingScoreMathable{

    @Override
    public int setRattingScore(Request request) {
        double main = request.getMainSubject();
        double second = request.getSecondSubject();
        double sub = request.getSubSubject();
        double average = (main+second+sub)/3.0;
        return (int) Math.round(average);
    }
}
