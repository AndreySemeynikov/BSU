package AndreySemeynikov.parts;

import AndreySemeynikov.InterfBD;

import java.io.PrintWriter;
import java.util.List;

public class Group implements InterfBD {
    private String name;
    private List<Team> teams;

    public Group(String name, List<Team> teams) {
        this.name = name;
        this.teams = teams;
    }

    public void processMatchResult(Match match) {
        Team team1 = findTeam(match.getTeam1());
        Team team2 = findTeam(match.getTeam2());

        team1.updateStatistics(match.getScore1(), match.getScore2());
        team2.updateStatistics(match.getScore2(), match.getScore1());
    }

    private Team findTeam(String teamName) {
        for (Team team : teams) {
            if (team.getName().equals(teamName)) {
                return team;
            }
        }
        return null;
    }

    @Override
    public void printTable(PrintWriter writer) {
        writer.println("Group " + name + ":");
        for (Team team : teams) {
            writer.println(team.getName() + " " +
                    team.getPoints() + " " +
                    team.getWins() + " " +
                    team.getDraws() + " " +
                    team.getLosses() + " " +
                    team.getGoalsScored() + " " +
                    team.getGoalsConceded());
        }
        writer.println();
    }
    public List<Team> getTeams() {
        return teams;
    }
    public String getName() {
        return name;
    }

}

