package com.qbros.testcomplete.service.models;

public enum ServiceErrCode implements ErrorCode {

    SERVICE_TIMEOUT(101),
    SERVICE_DOWN(102),
    ALREADY_EXITS(103),
    NOT_FOUND(104);

    private final int number;

    private ServiceErrCode(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

}
