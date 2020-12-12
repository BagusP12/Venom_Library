package com.venom;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;

        while (isContinue) {
            clearTerminal();

            System.out.println("===== Venom Library Book Database =====");
            System.out.println("1. \tBook List");
            System.out.println("2. \tSearch Book");
            System.out.println("3. \tAdd Book");
            System.out.println("4. \tUpdate Book");
            System.out.println("5. \tDelete Book");

            System.out.print("Select menu : ");
            byte userInput = scanner.nextByte();

            switch(userInput) {
                case 1:
                bookList();
                break;
                
                case 2:
                //Do Something
                break;

                case 3:
                //Do Something
                break;

                case 4:
                //Do Something
                break;

                case 5:
                //Do Something
                break;

                default:
                System.err.println("Wrong input (1-5) : ");
            }
            isContinue = continuePrompt();
        }

    }

    private static void bookList() {
        String query = "SELECT * FROM books";
    }

    private static boolean continuePrompt() {
        Scanner scanner = new Scanner (System.in);

        System.out.print("Do you want to continue? (y/n) : ");
        char userInput = scanner.next().charAt(0);

        if(Character.toLowerCase(userInput) == 'y')
            return true;
        else if (Character.toLowerCase(userInput) == 'n')
            return false;
        else
            return continuePrompt();
    }

    private static void clearTerminal() {
        try {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.err.print("Cannot clearing the terminal");
        }
    }

}
