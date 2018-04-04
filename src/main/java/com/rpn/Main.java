package com.rpn;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
                Scanner input = new Scanner(System.in);
                Calculator calculator = new Calculator();
                String val = "";
                System.out.println("welcome to rpn calculator, press # to exit");
                while (!val.equals("#")) {
                        val = input.nextLine();
                        calculator.parseAndCalculateString(val);
                }
                input.close();
                System.out.println("rpn calculator closed");
            }
    }
