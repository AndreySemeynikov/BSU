package AndreySemeynikov.parts;

public class Team {
    private String name;
    private int points;
    private int wins;
    private int draws;
    private int losses;
    private int goalsScored;
    private int goalsConceded;
    private int gamesPlayed;

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }
    public int getGamesPlayed() {
        return gamesPlayed;
    }
    public void updateStatistics(int goalsScored, int goalsConceded) {
        this.goalsScored += goalsScored;
        this.goalsConceded += goalsConceded;

        if (goalsScored > goalsConceded) {
            points += 3;
            wins++;
        } else if (goalsScored == goalsConceded) {
            points += 1;
            draws++;
        } else {
            losses++;
        }
        gamesPlayed++;
    }
}