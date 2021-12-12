package com.example.SpringMongoDB.Driver.pkg.DataStructure;

import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.Properties;

public class StackImpl {


    @Value("${SECRET_KEY}")
    private static String SECRET_KEY;
    public static final int maxSize = 6;
    int top;
    int arr[] = new int[maxSize];

    StackImpl() {
        top = -1;
    }

    boolean isEmpty() {

        if (top < 0) {
            System.out.println(" *** Stack Empty *** ");
            return true;
        } else {
            System.out.println(" *** Stack Not Empty *** ");
            return false;
        }

    }


    boolean  push(int element) {

        if (top < arr.length - 1) {
            arr[++top] = element;
            System.out.println(" *** Added " + element + " to Stack  *** ");
            return true;
        } else if (top >= arr.length - 1) {
            System.out.println(" *** Stack Overflow *** ");
            return false;
        } else {
            System.out.println(" *** Else Path in push() *** " + top);
            return false;
        }
    }

    int pop() {

        if (top < 0) {
            System.out.println(" *** Stack Underflow *** ");
            return -1;
        } else {
            int element = arr[top--];
            return element;
        }
    }

    int peek() {
        if (top < 0) {
            System.out.println(" *** Stack Overflow *** ");
            return -1;
        } else {
            int element = arr[top];
            return element;
        }

    }
    void print(){
        for(int i = top;i>-1;i--){
            System.out.print("  "+ arr[i]);
        }
    }


    public static void main(String args[]) throws UnsupportedEncodingException {
        StackImpl s = new StackImpl();
//        s.push(10);
//        s.push(20);
//        s.push(30);
//        System.out.println(s.pop() + " Popped from stack");
//        System.out.println("Top element is :" + s.peek());
//        System.out.print("Elements present in stack :");
//        s.print();
//        s.push(10);
//        s.push(10);
//        s.push(10);
//        s.push(10);
//        s.push(10);
//        s.push(10);
//        s.push(10);
//        s.print();

        try (InputStream input = new FileInputStream("D:\\Eclipse_Workspace\\SpringMySQLDB\\src\\main\\resources\\application.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println(prop.getProperty("uploadDir"));
            System.out.println(prop.getProperty("SECRET_KEY"));
           // System.out.println(prop.getProperty("db.password"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println(" SECRET KEY IS "+SECRET_KEY);

    }
}
