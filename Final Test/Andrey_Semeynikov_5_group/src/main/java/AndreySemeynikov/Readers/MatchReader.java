package AndreySemeynikov.Readers;

import AndreySemeynikov.parts.Match;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class MatchReader {
    public static List<Match> readMatchesFromFile(String fileName) throws IOException {
        List<Match> matches = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] matchInfo = line.split("\\s+");
                String team1 = matchInfo[0];
                int score1 = Integer.parseInt(matchInfo[1].split(":")[0]);

                int score2 = Integer.parseInt(matchInfo[1].split(":")[1]);
                String team2 = matchInfo[2];
                matches.add(new Match(team1, score1, team2, score2));
            }
        }
        return matches;
    }
}
