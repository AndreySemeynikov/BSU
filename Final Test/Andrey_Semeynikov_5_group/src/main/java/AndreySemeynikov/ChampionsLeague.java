package AndreySemeynikov;

import AndreySemeynikov.parts.Group;
import AndreySemeynikov.parts.Match;
import AndreySemeynikov.parts.Team;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class ChampionsLeague {
    private List<Group> groups;

    public ChampionsLeague(List<Group> groups) {
        this.groups = groups;
    }

    public void processMatches(List<Match> matches) {
        for (Match match : matches) {
            for (Group group : groups) {
                if (groupContainsTeam(group, match.getTeam1()) && groupContainsTeam(group, match.getTeam2())) {
                    group.processMatchResult(match);
                    break;
                }
            }
        }
    }

    private boolean groupContainsTeam(Group group, String teamName) {
        for (Team team : group.getTeams()) {
            if (team.getName().equals(teamName)) {
                return true;
            }
        }
        return false;
    }
}
