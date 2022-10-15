package edu.virginia.cs;

import java.util.HashMap;
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
                HashMap<String, Boolean> cat = filterCategories();
                dr = new DataReader("CAS_Research.xlsx");
                dr.readData(cat);
            }
            else if (institute.equals("E")) {
                HashMap<String, Boolean> cat = filterCategories();
                dr = new DataReader("SEAS_Research.xlsx");
                dr.readData(cat);
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

    private static HashMap<String, Boolean> filterCategories() {
        Scanner reader = new Scanner(System.in);
        boolean toContinue = true;
        String input4deselect = "";
        HashMap<String, Boolean> data = (new ResearchCategories()).getCategories();
        while (toContinue) {
            System.out.println("The following categories will be searched for.");
            for (String key: data.keySet()) {
                char mark = ' ';
                if (data.get(key))
                    mark = 'x';
                System.out.println(mark + " " + key);
            }
            System.out.print(" Would you like to proceed with this selection? (Y/N): ");
            if (reader.next().toUpperCase().equals("Y"))
                toContinue = false;

            if (toContinue) {
                System.out.print("Which category would you like to deselect? Enter only one: ");
                input4deselect = reader.next();
                data.put(input4deselect, false);
            }
        }
        return data;
    }
}
