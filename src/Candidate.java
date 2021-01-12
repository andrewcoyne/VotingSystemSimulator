import java.util.Random;

public class Candidate {
    String candidateName;
    double x; // from -10 to 10, -10 is the farthest left and 10 is the farthest right
    double y; // from -10 to 10, -10 is the most libertarian and 10 is the most authoritarian
    int votes;

    public Candidate() {
        Random r = new Random();
        candidateName = "Candidate #" + r.nextInt(10000);
        x = (r.nextDouble() * 20) - 10;
        y = (r.nextDouble() * 20) - 10;
        votes = 0;
    }

    public Candidate(String name, double econPos, double socPos) {
        candidateName = name;
        x = econPos;
        y = socPos;
        votes = 0;
    }

    public String getName() { return candidateName; }

    public double getEconomicPos() { return x; }

    public double getSocialPos() { return y; }

    public int getVotes() { return votes; }

    public void voteFor() { votes++; }

    public double getVoterProximity(double voterEconPos, double voterSocPos){
        double econPosDist = x - voterEconPos;
        double socPosDist = y - voterSocPos;
        return Math.sqrt((Math.pow(econPosDist,2)) + (Math.pow(socPosDist,2))); // Pythagorean Theorem
    }
}
