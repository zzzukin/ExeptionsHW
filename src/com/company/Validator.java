package com.company;

enum ValidatePINResult {success, failure};

enum ValidateSumResult {success, failure};

public class Validator {

    private final String PIN = "1234";

    public Integer GetPINAttemptsNum() {
        return PINAttemptsNum - PINFailureNum;
    }

    private Integer PINAttemptsNum = 3;
    private boolean IsPINSet = false;
    private Integer PINFailureNum = 0;

    public Integer GetPINFailureNum() {
        return PINAttemptsNum - PINFailureNum;
    }

    public void setPINAttemptsNum(Integer PINAttemptsNum) {
        this.PINAttemptsNum = PINAttemptsNum;
    }

    public void ResetPINFailureNum() {
        PINFailureNum = 0;
    }

    //валидация ПИна
    public ValidatePINResult ValidatePIN(String PIN) {
        ValidatePINResult Result = ValidatePINResult.failure;

        if (this.PIN.equals(PIN)) {
            Result = ValidatePINResult.success;
        } else {
            if (PINFailureNum != PINAttemptsNum) {
                PINFailureNum++;
            }
        }

        return Result;
    }

    //валидация суммы для зачисления / снятия
    public ValidateSumResult ValidateSum(double Sum) {
        return Sum % 100 == 0 ? ValidateSumResult.success : ValidateSumResult.failure;
    }
}
