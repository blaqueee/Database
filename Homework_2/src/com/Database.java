package com;

import com.Exceptions.*;
import com.FileService.FileReaderAndWriter;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Database implements Mutable{

    private Line[] lines;
    private boolean isConnectionOpened;

    public Database() {
        this.lines = FileReaderAndWriter.readFile();
        this.isConnectionOpened = false;
    }

    public Line[] getLines() {
        return lines;
    }

    @Override
    public void openConnection() throws ConnectionFailedException{

        int chance = new Random().nextInt(10) + 1;
        System.out.println("\n----- Trying to open connection... -----\n");

        if (chance > 5){
            isConnectionOpened = true;
            System.out.println("----- Connection has been successfully opened! -----\n");
        } else
            throw new ConnectionFailedException("Can't open connection! Try again!\n");
    }

    @Override
    public void closeConnection() throws ConnectionFailedException {
        if (!isConnectionOpened)
            throw new ConnectionFailedException("Connection is already closed!\n");
        isConnectionOpened = false;
        System.out.println("----- Connection successfully closed! -----");
    }

    @Override
    public boolean checkConnection() {
        return isConnectionOpened;
    }

    @Override
    public Line getLineByIndex() {
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.printf("Enter the index of line (0 - %s): ", lines.length - 1);
            try {
                String str = sc.nextLine().replaceAll("\\s+", "");
                int index = parseAndCheckIndex(str);
                return lines[index];
            } catch (NullPointerException | NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public boolean checkIsThereValueByKey() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the key: ");
            try {
                String key = sc.nextLine();
                checkKey(key);
                for (Line line : lines) {
                    if (line.getKey().equals(key)) {
                        if (line.getValue().equals("") || line.getValue() == null) {
                            System.out.println("Value of this key is empty!");
                            return false;
                        }
                        System.out.println("This key contains not empty value!");
                        return true;
                    }
                }
            } catch (KeyNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Line getLineByKey() {
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.print("Enter the key: ");
            try {
                String key = sc.nextLine();
                checkKey(key);
                for (Line line : lines){
                    if (line.getKey().equals(key))
                        return line;
                }
            } catch (KeyNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Line[] getArrayOfLinesByIndexes(int origin, int bound) {
        try {
            int from = parseAndCheckIndex(String.valueOf(origin));
            int to = parseAndCheckIndex(String.valueOf(bound));
            checkTwoIndexes(from, to);
            return Arrays.copyOfRange(lines, from, to);
        } catch (NullPointerException | NumberFormatException | IndexOutOfBoundsException | IncorrectIndexesException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public int getAmountOfLines() {
        return lines.length;
    }

    @Override
    public void insertLine() {
        Scanner sc = new Scanner(System.in);
        lines = Arrays.copyOf(lines, lines.length + 1);

        Line line = new Line();

        System.out.print("Set the key: ");
        String key = sc.nextLine();
        line.setKey(key);

        System.out.print("Set the value: ");
        String value = sc.nextLine();
        line.setValue(value);

        lines[lines.length - 1] = line;
        System.out.println("\nNew line has been successfully inserted to database\n");
    }
    @Override
    public void updateValueByIndex() {
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.print("Enter index: ");
            try {
                String str = sc.nextLine().replaceAll("\\s+", "");
                int index = parseAndCheckIndex(str);
                System.out.print("Set new value: ");
                lines[index].setValue(sc.nextLine());
                return;
            } catch (NullPointerException | NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void updateValueByKey() {
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.print("Enter key: ");
            try {
                String key = sc.nextLine();
                checkKey(key);
                for (Line line : lines){
                    if (line.getKey().equals(key)){
                        System.out.print("Set new value: ");
                        line.setValue(sc.nextLine());
                        return;
                    }
                }
            } catch (KeyNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void printDatabase(){
        System.out.println("+-------+---------------+-------------------------+");
        System.out.println("| INDEX | KEY           | VALUE                   |");
        System.out.println("+-------+---------------+-------------------------+");
        for (int i = 0; i < lines.length; i++){
            System.out.printf("| %-5s | %-13s | %-23s |%n", i, lines[i].getKey(), lines[i].getValue());
            System.out.println("+-------+---------------+-------------------------+");
        }
    }

    private int parseAndCheckIndex(String str) {
        if (str.equals(""))
            throw new NullPointerException("Index can't be empty!\n");
        int index = Integer.parseInt(str);

        if (index < 0 || index > lines.length - 1)
            throw new IndexOutOfBoundsException("Non-existent index!\n");
        return index;
    }

    private void checkKey(String key) throws KeyNotFoundException {
        for (Line line : lines){
            if (line.getKey().equals(key))
                return;
        }
        throw new KeyNotFoundException("Can't find this key!");
    }

    private void checkTwoIndexes(int origin, int bound) throws IncorrectIndexesException {
        if (origin >= bound)
            throw new IncorrectIndexesException("The first index can't be bigger then the second!");
    }
}
