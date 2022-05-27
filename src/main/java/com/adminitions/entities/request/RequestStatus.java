package com.adminitions.entities.request;

public enum RequestStatus {
    NOT_PROCESSED,
    BUDGET,
    RECOMMEND_BUDGET,
    CONTRACT,
    RECOMMEND_CONTRACT,
    ALLOWED,
    NOT_ALLOWED;

    public static RequestStatus getStatus(String status) {
        switch (status) {
            case "not processed":
                return RequestStatus.NOT_PROCESSED;
            case "budget":
                return RequestStatus.BUDGET;
            case "recommend budget":
                return RequestStatus.RECOMMEND_BUDGET;
            case "contract":
                return RequestStatus.CONTRACT;
            case "recommend contract":
                return RequestStatus.RECOMMEND_CONTRACT;
            case "allowed":
                return RequestStatus.ALLOWED;
            case "not allowed":
                return RequestStatus.NOT_ALLOWED;
            default:
                return RequestStatus.NOT_PROCESSED;
        }
    }

    public static String getStatusString(RequestStatus status){
        switch (status){
            case BUDGET:
                return "budget";
            case RECOMMEND_BUDGET:
                return "recommend budget";
            case CONTRACT:
                return "contract";
            case RECOMMEND_CONTRACT:
                return "recommend contract";
            case ALLOWED:
                return "allowed";
            case NOT_ALLOWED:
                return "not allowed";
            default:
                return "not processed";
        }
    }
}
