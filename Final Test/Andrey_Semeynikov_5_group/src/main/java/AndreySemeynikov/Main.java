package AndreySemeynikov;

import AndreySemeynikov.Readers.MatchReader;
import AndreySemeynikov.Readers.TeamReader;
import AndreySemeynikov.Writers.ResultsWriter;
import AndreySemeynikov.parts.Group;
import AndreySemeynikov.parts.Match;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            List<Group> groups = TeamReader.readGroupsFromFile("GroupsEN.txt");

            ChampionsLeague championsLeague = new ChampionsLeague(groups);

            List<Match> matches = MatchReader.readMatchesFromFile("GameEN.txt");
            championsLeague.processMatches(matches);

            ResultsWriter.writeResultsToFile(groups, "Results.txt");
            ResultsWriter.printTablesToFile(groups, "GroupsOut.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

