package com;

import com.Exceptions.*;

public interface Mutable {
    void openConnection() throws ConnectionFailedException;
    void closeConnection() throws ConnectionFailedException;
    boolean checkConnection();
    Line getLineByIndex();
    boolean checkIsThereValueByKey();
    Line getLineByKey();
    Line[] getArrayOfLinesByIndexes(int origin, int bound);
    int getAmountOfLines();
    void insertLine();
    void updateValueByIndex();
    void updateValueByKey();
}
