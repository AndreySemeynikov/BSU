package AndreySemeynikov.Readers;

import AndreySemeynikov.parts.Group;
import AndreySemeynikov.parts.Team;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamReader {
    public static List<Group> readGroupsFromFile(String fileName) throws IOException {
        List<Group> groups = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] teamNames = line.split("\\s+");
                String groupName = teamNames[0];
                List<Team> teams = new ArrayList<>();
                for (int i = 1; i < teamNames.length; i++) {
                    teams.add(new Team(teamNames[i]));
                }
                groups.add(new Group(groupName, teams));
            }
        }
        return groups;
    }
}
