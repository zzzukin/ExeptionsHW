package com.company;

interface ITerminal {
    //ввод ПИНа
    boolean SetPIN(String P) throws CTerminal.AccountIsLockedException;

    //кол-во попыток ввода ПИНа
    public Integer GetPINAtteptsNum();

    //запрос времени разблокировки аккаунта
    public double getTimer();

    //статус установки ПИНа
    public boolean isPINSet();

    //запрос счета в банке
    String RequestBalance() throws CTerminal.AccountIsLockedException;

    //снятие наличных
    String GetCash(double Cash) throws CTerminal.AccountIsLockedException;

    //зачислене наличных
    String SetCash(double Cash) throws CTerminal.AccountIsLockedException;
}
