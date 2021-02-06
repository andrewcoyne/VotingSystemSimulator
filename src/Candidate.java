import java.util.Random;

public class Candidate {
    String candidateName;
    double econPos; // from -10 to 10, -10 is the farthest left and 10 is the farthest right
    double socialPos; // from -10 to 10, -10 is the most libertarian and 10 is the most authoritarian
    int votes;

    public Candidate() {
        Random r = new Random();
        candidateName = "Candidate #" + r.nextInt(10000);
        econPos = (r.nextDouble() * 20) - 10;
        socialPos = (r.nextDouble() * 20) - 10;
        votes = 0;
    }

    public Candidate(String name, double econPos, double socPos) {
        candidateName = name;
        this.econPos = econPos;
        socialPos = socPos;
        votes = 0;
    }

    public int getVotes(){
        return votes;
    }

    public void voteFor() { votes++; }

    public double getVoterProximity(double voterEconPos, double voterSocPos){
        double econPosDist = econPos - voterEconPos;
        double socPosDist = socialPos - voterSocPos;
        return Math.sqrt((Math.pow(econPosDist,2)) + (Math.pow(socPosDist,2))); // Pythagorean Theorem
    }
}
