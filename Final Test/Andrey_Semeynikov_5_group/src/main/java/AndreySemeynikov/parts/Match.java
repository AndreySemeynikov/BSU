package AndreySemeynikov.parts;

public class Match {

    private String team1;
    private String team2;
    private int score1;
    private int score2;

    public Match(String team1, int score1, String team2, int score2) {
        this.team1 = team1;
        this.score1 = score1;
        this.team2 = team2;
        this.score2 = score2;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }
}