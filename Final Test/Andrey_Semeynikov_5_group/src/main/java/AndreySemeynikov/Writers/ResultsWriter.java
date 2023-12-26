package AndreySemeynikov.Writers;

import AndreySemeynikov.parts.Group;
import AndreySemeynikov.parts.Team;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ResultsWriter {
    public static void writeResultsToFile(List<Group> groups, String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (Group group : groups) {
                List<Team> sortedTeams = group.getTeams().stream()
                        .sorted(Comparator
                                .comparingInt(Team::getPoints)
                                .thenComparingInt(Team::getGamesPlayed)
                                .reversed())
                        .collect(Collectors.toList());

                writer.println("Group " + group.getName() + ":");
                for (Team team : sortedTeams) {
                    writer.println(team.getName() + " " +
                            team.getPoints() + " " +
                            team.getGamesPlayed());
                }
                writer.println();
            }
        }
    }
    public static void printTablesToFile(List<Group> groups, String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (Group group : groups) {
                group.printTable(writer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}