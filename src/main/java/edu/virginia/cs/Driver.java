package edu.virginia.cs;

import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        welcomeMessage();
        Scanner reader = new Scanner(System.in);
        String role = reader.next().toUpperCase();
        if (isStudent(role)) {
            System.out.println("Enter C if you are interested in research opportunities with the College of Arts and " +
                    "Sciences or E if you are interested in research opportunities with the Engineering School");
            String institute = reader.next().toUpperCase();

            DataReader dr;
            if (institute.equals("C")) {
                dr = new DataReader("CAS_Research.xlsx");
                dr.readData();
            }
            else if (institute.equals("E")) {
                dr = new DataReader("SEAS_Research.xlsx");
                dr.readData();
            }
            else
                throw new IllegalArgumentException("Must enter C(ollege of Arts and Science) or E(ngineering School) to " +
                        "view applicable research opportunities");
        }
        else if (isProjectManager(role)) {

        }
        else
            throw new IllegalArgumentException("Must enter S(tudent) or P(roject Manager) for role. Please tru again");
    }

    private static boolean isProjectManager(String role) {
        return role.equals("P");
    }

    private static boolean isStudent(String role) {
        return role.equals("S");
    }

    private static void welcomeMessage() {
        System.out.println("Welcome to the Mirabilis System: an application that cuts through the noise of UVA's " +
                "research opportunities");
        System.out.println("Please enter (S) if you are a student or (P) if you are a Project Manager");
    }

    private static void provideFilterMessage() {
        System.out.println("The following categories are available. If you would like to ");
    }
}
