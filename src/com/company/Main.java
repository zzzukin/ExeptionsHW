package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ITerminal Terminal = new CTerminal();

        Scanner Scan = new Scanner(System.in);

        boolean Quit = false;
        while (!Quit) {
            if (!Terminal.isPINSet()) {

                System.out.println("1. Enter PIN");
                System.out.println("2. Quit");

                if (Scan.hasNext()) {

                    switch (Scan.nextLine()) {
                        case "1":
                            System.out.print("PIN: ");
                            if (Scan.hasNext()) {
                                try {
                                    if (!Terminal.SetPIN(Scan.nextLine())) continue;
                                } catch (CTerminal.AccountIsLockedException e) {
                                    System.out.println(e.getMessage());
                                    System.out.println("Timout: " + Terminal.getTimer());
                                    continue;
                                }
                            }

                            System.out.println("Entering PIN success!\n");
                            break;

                        case "2":
                            Quit = true;
                            break;
                    }
                }
            }

            if(!Quit) {
                System.out.println("1. Balance");
                System.out.println("2. Get cash");
                System.out.println("3. Send cash");
                System.out.println("4. Quit");

                double Cash = 0;
                String TerminalAnswer = "";

                if (Scan.hasNext()) {
                    try {
                        switch (Scan.nextLine()) {

                            case "1":
                                TerminalAnswer = Terminal.RequestBalance();
                                System.out.println(TerminalAnswer);
                                break;

                            case "2":
                                System.out.print("Enter cash sum: ");
                                Cash = Integer.parseInt(Scan.nextLine());
                                TerminalAnswer = Terminal.GetCash(Cash);
                                System.out.println(TerminalAnswer);
                                break;

                            case "3":
                                System.out.print("Enter cash sum: ");
                                Cash = Integer.parseInt(Scan.nextLine());
                                TerminalAnswer = Terminal.SetCash(Cash);
                                System.out.println(TerminalAnswer);

                                break;

                            case "4":
                                Quit = true;
                                break;
                        }
                    } catch (CTerminal.AccountIsLockedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

        System.out.println("Bye!");
    }
}
