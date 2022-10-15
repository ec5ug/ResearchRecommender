package edu.virginia.cs;

import java.util.Locale;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        welcomeMessage();
        Scanner reader = new Scanner(System.in);
        String role = reader.next().toUpperCase();
        if (role.equals("S"))
            System.out.println("Student");
        else if (role.equals("P"))
            System.out.println("Project Manager");
        else
            throw new IllegalArgumentException("Must enter S(tudent) or P(roject Manager) for role. Please tru again");

    }

    private static void welcomeMessage() {
        System.out.println("Welcome to the Mirabilis System: an application that cuts through the noise of UVA's " +
                "research opportunities");
        System.out.println("Please enter (S) if you are a student or (P) if you are a Project Manager");

    }
}
