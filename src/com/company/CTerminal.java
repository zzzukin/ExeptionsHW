package com.company;

import java.util.*;

public class CTerminal implements ITerminal {

    private boolean IsPINSet = false;
    private com.company.Validator Validator = new Validator();
    private Date TimerDate = new Date();
    private long TimerTime = 0;
    private long LockTime = 5000;

    private Timer TimeOutTimer = new Timer();
    TimeOutTimerTask TimerTask = new TimeOutTimerTask();

    public boolean isPINSet() {
        return IsPINSet;
    }

    private class TimeOutTimerTask extends TimerTask {
        @Override
        public void run() {
            //System.out.println("Account unlocked!");
            Validator.ResetPINFailureNum();
            TimeOutTimer = new Timer();
            TimerTask = new TimeOutTimerTask();
        }
    }

    public double getTimer() {
        return (TimerTask.scheduledExecutionTime() - new Date().getTime()) * 1e-3;
    }

    @Override
    public Integer GetPINAtteptsNum() {
        return Validator.GetPINAttemptsNum();
    }

    @Override
    public boolean SetPIN(String P) throws AccountIsLockedException {

        ValidatePINResult Res = Validator.ValidatePIN(P);

        if (Res == ValidatePINResult.success) {
            IsPINSet = true;
        }

        if (Res == ValidatePINResult.failure) {

            System.out.println("PIN number is incorrect!");

            Integer AtteptsNum = Validator.GetPINAttemptsNum();

            if (AtteptsNum != 0) {
                System.out.println("Attempts number: " + AtteptsNum);
            } else {
                if (TimerTask.scheduledExecutionTime() == 0) {
                    TimeOutTimer.schedule(TimerTask, LockTime);
                    TimerTime = TimerDate.getTime();

                    System.out.println("Timeout is: " + getTimer());
                }
                throw new AccountIsLockedException("Account is locked exeption");
            }
        }

        return Res == ValidatePINResult.success ? true : false;
    }

    TerminalServer Server = new TerminalServer();

    @Override
    public String RequestBalance() throws AccountIsLockedException {
        if (!IsPINSet) throw new AccountIsLockedException("Account is locked exeption");

        String Mes = "";
        String ServerMes = "";

        try {
            ServerMes = Server.RequestBalance();
            Mes = "Your balance is: " + ServerMes + " rubles";
        } catch (TerminalServer.ServerDisconnectedExeption e) {
            Mes = e.getMessage() + "\nReapeat operation after few seconds";
        }

        return Mes;
    }

    @Override
    public String SetCash(double Cash) throws AccountIsLockedException {
        if (!IsPINSet) throw new AccountIsLockedException("Account is locked exeption");

        String Mes = "";
        String ServerMes = "";

        if (Validator.ValidateSum(Cash) == ValidateSumResult.success) {
            try {
                ServerMes = Server.setCash(Cash);
                Mes = "Your sum " + Cash + " is credit to account\nYour balance is: " + ServerMes + " rubles";
            } catch (TerminalServer.ServerDisconnectedExeption e) {
                Mes = e.getMessage() + "\nReapeat operation after few seconds";
            }
        } else Mes = "Incorrect sum. Sum must be multiple of 100";

        return Mes;
    }

    @Override
    public String GetCash(double Cash) throws AccountIsLockedException {
        if (!IsPINSet) throw new AccountIsLockedException("Account is locked exeption");

        String Mes = "";
        String ServerMes = "";

        if (Validator.ValidateSum(Cash) == ValidateSumResult.success) {
            try {
                ServerMes = Server.getCash(Cash);
                Mes = "Get you cash " + Cash + " rubles\nYour balance is: " + ServerMes + " rubles";
            } catch (TerminalServer.ServerInsufficientFundsInAccount e) {
                Mes = e.getMessage() + "\nPlease enter a lower sum";
            } catch (TerminalServer.ServerDisconnectedExeption e) {
                Mes = e.getMessage() + "\nReapeat operation after few seconds";
            }

        } else Mes = "Incorrect sum. Sum must be multiple of 100";

        return Mes;
    }

    //исключения
    public class AccountIsLockedException extends Throwable {
        String ExeptionMes;

        AccountIsLockedException(String s) {
            ExeptionMes = s;
        }

        @Override
        public String getMessage() {
            return ExeptionMes;
        }
    }
}
