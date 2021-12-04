package com.example.SpringMongoDB.Driver.pkg.DataStructure;

public class StackImpl {
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


    public static void main(String args[]) {
        StackImpl s = new StackImpl();
//        s.push(10);
//        s.push(20);
//        s.push(30);
//        System.out.println(s.pop() + " Popped from stack");
//        System.out.println("Top element is :" + s.peek());
//        System.out.print("Elements present in stack :");
//        s.print();
        s.push(10);
        s.push(10);
        s.push(10);
        s.push(10);
        s.push(10);
        s.push(10);
        s.push(10);
        s.print();




    }
}
