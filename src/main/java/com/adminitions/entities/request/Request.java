package com.adminitions.entities.request;

import com.adminitions.entities.Entity;

import java.sql.Time;

public class Request extends Entity {
    private RequestStatus status;
    private int facultiesId;
    private int applicantId;
    private int mainSubject;
    private int secondSubject;
    private int subSubject;
    private int ratingScore;
    private float averageAttestationScore;
    private Time publishTime;

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public int getFacultiesId() {
        return facultiesId;
    }

    public void setFacultiesId(int facultiesId) {
        this.facultiesId = facultiesId;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public int getMainSubject() {
        return mainSubject;
    }

    public void setMainSubject(int mainSubject) {
        this.mainSubject = mainSubject;
    }

    public int getSecondSubject() {
        return secondSubject;
    }

    public void setSecondSubject(int secondSubject) {
        this.secondSubject = secondSubject;
    }

    public int getSubSubject() {
        return subSubject;
    }

    public void setSubSubject(int subSubject) {
        this.subSubject = subSubject;
    }

    public int getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(int ratingScore) {
        this.ratingScore = ratingScore;
    }

    public float getAverageAttestationScore() {
        return averageAttestationScore;
    }

    public void setAverageAttestationScore(float averageAttestationScore) {
        this.averageAttestationScore = averageAttestationScore;
    }

    public Time getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Time publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + getId() +
                ", status=" + status +
                ", facultiesId=" + facultiesId +
                ", applicantId=" + applicantId +
                ", mainSubject=" + mainSubject +
                ", secondSubject=" + secondSubject +
                ", subSubject=" + subSubject +
                ", ratingScore=" + ratingScore +
                ", averageAttestationScore=" + averageAttestationScore +
                ", publishTime=" + publishTime +
                '}';
    }
}
