package com.rpn;

import java.util.ArrayList;

public class UndoRecord {
    private int index;
    private ArrayList<Double> valueArray;

    public UndoRecord(int index, ArrayList<Double> valueArray){
        this.index = index;
        this.valueArray = valueArray;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<Double> getValueArray() {
        return valueArray;
    }

    public void setValueArray(ArrayList<Double> valueArray) {
        this.valueArray = valueArray;
    }
}


