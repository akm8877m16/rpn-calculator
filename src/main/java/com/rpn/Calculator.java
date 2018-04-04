package com.rpn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Calculator {

    protected static HashMap operatorMap = new HashMap();

    static {
        operatorMap.put("+",1);
        operatorMap.put("-",2);
        operatorMap.put("/",3);
        operatorMap.put("*",4);
        operatorMap.put("sqrt",5);
        operatorMap.put("undo",6);
        operatorMap.put("clear",7);
        operatorMap.put("#",8);
    }

    private Stack<Double> valueStack;
    private Stack<UndoRecord> undoRecords;
    private String currentFomula;

    private static final String ERROR_INSUFFICIENT = "insufficient parameters";
    private static final String ERROR_ZERO_DIVIDE = "divide by zero";
    private static final String ERROR_NEGATIVE_SQRT = "sqrt negative value";
    private static final String ERROR_ILLEGAL_PARAM = "parameter error";

    public Calculator(){
        valueStack = new Stack<Double>();
        undoRecords = new Stack<UndoRecord>();
    }

    public void parseAndCalculateString(String val){
        this.currentFomula = val;
        String[] splitArray = val.split(" ");
        for(int i=0;i<splitArray.length;i++ ){
            String string = splitArray[i];
            string = string.trim();
            if(isDigit(string)){
                valueStack.push(Double.parseDouble(string));
                addUndoRecords(valueStack.size()-1);
            }else if(isOperator(string)){
                if(checkError(string)){
                    calculate(string);
                }else{
                    printStack();
                    return;
                }
            }else{
                int position = currentFomula.indexOf(string);
                System.out.printf("unknown operator %s (position: %d): %s%n",string,position,ERROR_ILLEGAL_PARAM);
                printStack();
                return;
            }
        }
        printStack();
    }

    private Boolean isDigit(String s){
        try {
            Double.parseDouble(s);
        }catch(Exception e)
        {
            return false;
        }
        return true;
    }

    private Boolean isOperator(String s){
        return operatorMap.containsKey(s);
    }

    private void undo(){
        if(!undoRecords.isEmpty()){
            UndoRecord lastRecord = undoRecords.pop();
            valueStack.remove(lastRecord.getIndex());
            ArrayList<Double> valueList = lastRecord.getValueArray();
            if(!valueList.isEmpty()){
                for(int i=0;i<valueList.size();i++){
                    valueStack.add(lastRecord.getIndex(),valueList.get(i));
                }
            }
        }
        return;
    }

    private void calculate(String operator){
        if(checkError(operator)){
            switch ((Integer) operatorMap.get(operator)){
                case 1:
                    double value1 = valueStack.pop();
                    double value2 = valueStack.pop();
                    double value = value1+value2;
                    valueStack.push(value);
                    addUndoRecords(valueStack.size()-1,value1,value2);
                    break;
                case 2:
                    value1 = valueStack.pop();
                    value2 = valueStack.pop();
                    value = value2 - value1;
                    valueStack.push(value);
                    addUndoRecords(valueStack.size()-1,value1,value2);
                    break;
                case 3:
                    value1 = valueStack.pop();
                    value2 = valueStack.pop();
                    value = value2/value1;
                    valueStack.push(value);
                    addUndoRecords(valueStack.size()-1,value1,value2);
                    break;
                case 4:
                    value1 = valueStack.pop();
                    value2 = valueStack.pop();
                    value = value1*value2;
                    valueStack.push(value);
                    addUndoRecords(valueStack.size()-1,value1,value2);
                    break;
                case 5:
                    value1 = valueStack.pop();
                    value = Math.sqrt(value1);
                    valueStack.push(value);
                    addUndoRecords(valueStack.size()-1,value1);
                    break;
                case 6:
                    undo();
                    break;
                case 7:
                    clear();
                    break;
                default:
                    break;
            }
        }else{
            printStack();
        }
    }

    private void clear(){
        valueStack.clear();
        undoRecords.clear();
    }

    private Boolean checkError(String operator){
        Integer operatorType= (Integer)operatorMap.get(operator);
        if(operatorType == 1 || operatorType == 2 || operatorType == 4){
            if(valueStack.size() < 2){
                int position = currentFomula.indexOf(operator);
                System.out.printf("operator %s (position: %d): %s%n",operator,position,ERROR_INSUFFICIENT);
                return false;
            }
        }else if(operatorType == 3){
            if(valueStack.size() < 2){
                int position = currentFomula.indexOf(operator);
                System.out.printf("operator %s (position: %d): %s%n",operator,position,ERROR_INSUFFICIENT);
                return false;
            }else if( (double)valueStack.peek() == 0 ){
                int position = currentFomula.indexOf(operator);
                System.out.printf("operator %s (position: %d): %s%n",operator,position,ERROR_ZERO_DIVIDE);
                return false;
            }
        }else if(operatorType == 5){
            if(valueStack.empty()){
                int position = currentFomula.indexOf(operator);
                System.out.printf("operator %s (position: %d): %s%n",operator,position,ERROR_INSUFFICIENT);
                return false;
            }else if((double)valueStack.peek() < 0){
                int position = currentFomula.indexOf(operator);
                System.out.printf("operator %s (position: %d): %s%n",operator,position,ERROR_NEGATIVE_SQRT);
                return false;
            }
        }
        return true;
    }

    public String printStack(){
        StringBuilder stringBuilder = new StringBuilder().append("stack: ");
        if(valueStack.isEmpty()){
            System.out.println(stringBuilder.toString());
        }else{
            for(int i=0;i<valueStack.size();i++){
                double stackValue = valueStack.get(i);
                if( Math.floor(stackValue * 10) %10 == 0){
                    stringBuilder.append((int)stackValue).append(" ");
                }else{
                    stringBuilder.append(stackValue).append(" ");
                }
            }
            System.out.println(stringBuilder.toString().trim());
        }
        return stringBuilder.toString().trim();
    }

    private void addUndoRecords(int currentIndex, double ...values){
        ArrayList<Double> undoList = new ArrayList<Double>();
        for(int i=0;i< values.length;i++){
            undoList.add(values[i]);
        }
        undoRecords.push(new UndoRecord(currentIndex,undoList));
    }

}
