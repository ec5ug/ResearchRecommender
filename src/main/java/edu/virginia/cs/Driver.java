package edu.virginia.cs;

import java.util.HashMap;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        welcomeMessage();
        chooseFileToRead();
    }
    private static void chooseFileToRead() {
        Scanner reader = new Scanner(System.in);
        String institute = reader.next().toUpperCase();
        if (institute.equals("C"))
            executeDataRead("CAS_Research.xlsx");
        else if (institute.equals("E"))
            executeDataRead("SEAS_Research.xlsx");
        else
            throw new IllegalArgumentException("Must enter C(ollege of Arts and Science) or E(ngineering School) to " +
                    "view applicable research opportunities");
    }

    private static void executeDataRead(String filename) {
        DataReader dr;
        HashMap<String, Boolean> cat = filterCategories();
        dr = new DataReader(filename);
        dr.readData(cat);
    }

    private static void welcomeMessage() {
        System.out.println("Welcome to the Mirabilis System: an application that cuts through the noise of UVA's " +
                "research opportunities");
        System.out.print("Enter C if you are interested in research opportunities with the College of Arts and " +
                "Sciences or E if you are interested in research opportunities with the Engineering School: ");
    }

    private static HashMap<String, Boolean> filterCategories() {
        Scanner reader = new Scanner(System.in);
        boolean toContinue = true;
        String input4deselect;
        HashMap<String, Boolean> data = (new ResearchCategories()).getCategories();
        
        while (toContinue) {
            System.out.println("The following categories will be searched for.");
            printHashMap(data);
            System.out.print("Would you like to proceed with this selection? (Y/N): ");
            toContinue = updateToUserInput(reader, toContinue);

            if (toContinue) {
                System.out.print("Which category would you like to deselect? Enter only one: ");
                input4deselect = reader.next();
                data.put(input4deselect, false);
            }
        }
        return data;
    }

    private static boolean updateToUserInput(Scanner reader, boolean toContinue) {
        if (reader.next().equalsIgnoreCase("Y"))
            toContinue = false;
        return toContinue;
    }

    private static void printHashMap(HashMap<String, Boolean> data) {
        for (String key: data.keySet()) {
            char mark = ' ';
            if (data.get(key))
                mark = 'x';
            System.out.println(mark + " " + key);
        }
    }
}
