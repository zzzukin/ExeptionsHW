package com.company;

import java.util.Timer;

public class TerminalServer {
    private double TotalCash = 1e+6;
    private boolean IsServerConnected = true;
    private final int ReconnectServerTime = 10000;

    TerminalServer() {
        ReconnectTimer.schedule(TimerTask, ReconnectServerTime, ReconnectServerTime);
    }

    private Timer ReconnectTimer = new Timer();
    TerminalServer.TimerTask TimerTask = new TimerTask();

    private class TimerTask extends java.util.TimerTask {
        @Override
        public void run() {
            IsServerConnected = IsServerConnected ? false : true;
        }
    }

    public String RequestBalance() throws ServerDisconnectedExeption {
        if (!IsServerConnected) throw new ServerDisconnectedExeption("Server is disconnected!");
        return String.valueOf(TotalCash);
    }

    public String getCash(double Cash) throws ServerInsufficientFundsInAccount, ServerDisconnectedExeption {
        if (!IsServerConnected) throw new ServerDisconnectedExeption("Server is disconnected!");

        String Mes = String.valueOf(TotalCash);
        if (TotalCash - Cash >= 0) {
            TotalCash -= Cash;
            Mes = String.valueOf(TotalCash);
        } else {
            throw new ServerInsufficientFundsInAccount("Insufficient funds in the account");
        }

        return Mes;
    }

    public String setCash(double Cash) throws ServerDisconnectedExeption {
        if (!IsServerConnected) throw new ServerDisconnectedExeption("Server is disconnected!");
        TotalCash += Cash;
        return String.valueOf(TotalCash);
    }

    //исключения
    public class ServerDisconnectedExeption extends Throwable {
        String ExeptionMes;

        ServerDisconnectedExeption(String s) {
            ExeptionMes = s;
        }

        @Override
        public String getMessage() {
            return ExeptionMes;
        }
    }

    public class ServerInsufficientFundsInAccount extends Throwable {
        String ExeptionMes;

        ServerInsufficientFundsInAccount(String s) {
            ExeptionMes = s;
        }

        @Override
        public String getMessage() {
            return ExeptionMes;
        }
    }
}
