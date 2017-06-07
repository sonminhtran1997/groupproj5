package cs143.ui;

import cs143.business.RetireeManager;
import cs143.domain.Retiree;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SsnMapApp {
    
    private static Scanner scanIn = new Scanner(System.in);
    private static RetireeManager rm = new RetireeManager();

    public static void main(String[] args) {
        long input = 0;
        do {
            input = displayMenu();
            if (input == 0) {
                delete();
            } else if (input == 1) {
                add();
            } else if (input > 1) {
                get(input);
            }
        } while (input >= 0);
    }
    
     public static long displayMenu() {
        System.out.println("Select an option:\n   -1 Exit"
                + "\n    0 Delete a Retiree\n    1 Add a Retiree"
                + "\n   SSN Get Monthly Benefits");
        long input = 0;
        boolean ok = true;
        do {
            ok = true;
            try {
                System.out.print("Please enter the option: ");
                input = scanIn.nextLong();
            } catch (Exception ex) {
                ok = false;
                System.out.println("The input is not valid");
                scanIn.nextLine();
            }
        } while (!ok);
        return input;
    }

    
    public static void delete() {
        boolean valid = false;
        do {
            System.out.print("SSN > ");
            try {
                long ssn = scanIn.nextLong();
                if (ssn >= 100000000 && ssn <= 999999999) {
                    valid = true;
                    if (rm.delete(ssn)) {
                        System.out.println("Retiree deleted.");
                    } else {
                        System.out.println("Error while deleting the retiree.");
                        System.out.println("The retiree might not exist or the database is defective");
                    }
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException ime) {
                System.out.println("Please enter a valid SSN");
                scanIn.nextLine();
            }
        } while (!valid);
        System.out.println();
    }
    
    public static void add() {
        String name = "";
        long ssn = 0;
        double benefits = 0;
        boolean valid = false;
        do {
            System.out.print("SSN > ");
            try {
                scanIn = new Scanner(System.in);
                ssn = scanIn.nextLong();
                if (ssn >= 100000000 && ssn <= 999999999) {
                    valid = true;
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException ime) {
                System.out.println("Please enter a valid SSN");
            }
        } while (!valid);
        System.out.print("Name > ");
        scanIn = new Scanner(System.in);
        name = scanIn.nextLine();
        valid = false;
        do {
            System.out.print("Benefits > ");
            try {
                benefits = scanIn.nextDouble();
                if (benefits >= 0) {
                    valid = true;
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException ime) {
                System.out.println("The monthly benefits should be a positive number");
                scanIn.nextLine();
            }
        } while (!valid);
        if (rm.add(new Retiree(ssn, name, benefits))) {
            System.out.println("Retiree added.");
        } else {
            System.out.println("Error while adding the retiree.");
            System.out.println("A retiree with the same SSN is already present in the database");
        }
        System.out.println();
    }
    
    public static void get(long ssn) {
        if (ssn >= 100000000 && ssn <= 999999999) {
            Retiree retiree = rm.get(ssn);
            if (retiree == null) {
                System.out.println("The given SSN does not exist");
            } else {
                System.out.println(retiree);
            }
        } else {
            System.out.println("Invalid SSN.");
        }
        System.out.println();
    }
    
}
