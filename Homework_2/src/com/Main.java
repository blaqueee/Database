package com;

import com.Exceptions.ChoiceNotFoundException;
import com.Exceptions.ConnectionFailedException;
import com.Exceptions.EmptyChoiceException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        run();
        sc.close();
    }

    private static void run(){
        Database database = new Database();
        doAction(database);
    }

    private static void printActions(){
        System.out.println("""
                1 -> Open connection with database
                2 -> Close connection with database
                3 -> Check connection status
                4 -> Get line by index
                5 -> Check is there value by key
                6 -> Get line by key
                7 -> Get array by two indexes (first and last)
                8 -> Get amount of lines of database
                9 -> Insert new line to database
                10 -> Update value by index
                11 -> Update value by key
                12 -> Print database
                """);
    }

    private static void doAction(Database database){
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        while (true) {
            printActions();
            System.out.print("Enter the number of action: ");

            try {
                String str = sc.nextLine().replaceAll("\\s+", "");
                choice = parseAndCheckAction(str);
            } catch (EmptyChoiceException | NumberFormatException | ChoiceNotFoundException e) {
                System.out.println(e.getMessage());
                continue;
            }

            switch (choice){
                case 1:
                    try {
                        database.openConnection();
                    } catch (ConnectionFailedException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        database.closeConnection();
                    } catch (ConnectionFailedException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    if (database.checkConnection())
                        System.out.println("Connection is opened!");
                    else
                        System.out.println("Connection is closed!");
                    break;
                case 4:
                    if (database.checkConnection()){
                        printLine(database.getLineByIndex());
                    } else
                        System.out.println("Connection is closed!");
                    break;
                case 5:
                    if (database.checkConnection()){
                        database.checkIsThereValueByKey();
                    } else
                        System.out.println("Connection is closed!");
                    break;
                case 6:
                    if (database.checkConnection()){
                        printLine(database.getLineByKey());
                    } else
                        System.out.println("Connection is closed!");
                    break;
                case 7:
                    if (database.checkConnection()){
                        while (true) {
                            try {
                                System.out.print("Enter the first index: ");
                                String str = sc.nextLine().replaceAll("\\s+", "");
                                int first = checkIndex(str, database.getLines());

                                System.out.print("Enter the last index: ");
                                String str1 = sc.nextLine().replaceAll("\\s+", "");
                                int last = checkIndex(str1, database.getLines());

                                printArrayOfLines(database.getArrayOfLinesByIndexes(first, last));
                                break;
                            } catch (EmptyChoiceException | IndexOutOfBoundsException | NumberFormatException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    } else
                        System.out.println("Connection is closed!");
                    break;
                case 8:
                    if (database.checkConnection()){
                        System.out.printf("%nDatabase contains %s lines.%n%n", database.getAmountOfLines());
                    } else
                        System.out.println("Connection is closed!");
                    break;
                case 9:
                    if (database.checkConnection()){
                        database.insertLine();
                    } else
                        System.out.println("Connection is closed!");
                    break;
                case 10:
                    if (database.checkConnection()){
                        database.updateValueByIndex();
                    } else
                        System.out.println("Connection is closed!");
                    break;
                case 11:
                    if (database.checkConnection()){
                        database.updateValueByKey();
                    } else
                        System.out.println("Connection is closed!");
                    break;
                case 12:
                    if (database.checkConnection()){
                        database.printDatabase();
                    } else
                        System.out.println("Connection is closed!");
                    break;
            }
        }
    }

    private static int parseAndCheckAction(String str) throws EmptyChoiceException, ChoiceNotFoundException {
        if (str.equals(""))
            throw new EmptyChoiceException("Choice can't be empty!");

        int choice = Integer.parseInt(str);

        if (choice < 1 || choice > 12)
            throw new ChoiceNotFoundException("Can't find this choice!");

        return choice;
    }

    private static void printLine(Line line){
        System.out.println("+---------------+-------------------------+");
        System.out.println("| KEY           | VALUE                   |");
        System.out.println("+---------------+-------------------------+");
        System.out.printf("| %-13s | %-23s |%n", line.getKey(), line.getValue());
        System.out.println("+---------------+-------------------------+");
    }

    private static void printArrayOfLines(Line[] lines){
        System.out.println("+---------------+-------------------------+");
        System.out.println("| KEY           | VALUE                   |");
        System.out.println("+---------------+-------------------------+");
        for (Line line : lines) {
            System.out.printf("| %-13s | %-23s |%n", line.getKey(), line.getValue());
            System.out.println("+---------------+-------------------------+");
        }
    }

    private static int checkIndex(String str, Line[] lines) throws EmptyChoiceException{
        if (str.equals(""))
            throw new EmptyChoiceException("Index can't be empty!");

        int index = Integer.parseInt(str);

        if (index < 0 || index > lines.length - 1)
            throw new IndexOutOfBoundsException("Can't find this index!");

        return index;
    }
}
