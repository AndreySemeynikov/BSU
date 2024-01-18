package AndreySemeynikov;

import AndreySemeynikov.ui.GUI.GUI;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException, JAXBException {
        //TaskFileManager taskFileManager = new TaskFileManager();
        //ConsoleUI consoleUI = new ConsoleUI(taskFileManager);
        //consoleUI.startConsole();

        GUI.launch(GUI.class, args);
    }


}
