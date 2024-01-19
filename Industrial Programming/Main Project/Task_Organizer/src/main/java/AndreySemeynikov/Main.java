package AndreySemeynikov;

import AndreySemeynikov.tasks.read_and_write.TaskFileManager;
import AndreySemeynikov.ui.ConsoleUI;
import AndreySemeynikov.ui.GUI.GUI;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) throws IOException, JAXBException {
        System.out.println("What interface do you want to use?\n1.Console\n2.GUI");
        int choice = readUserChoice();
        switch (choice) {
            case 1:
                TaskFileManager taskFileManager = new TaskFileManager();
                ConsoleUI consoleUI = new ConsoleUI(taskFileManager);
                consoleUI.startConsole();
                break;
            case 2:
                GUI.launch(GUI.class, args);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    public static int readUserChoice() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter your choice: ");
            return scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid choice.");
            return -1;
        }
    }
}
