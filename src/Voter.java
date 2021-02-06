import java.util.ArrayList;
import java.util.Random;

public class Voter {
    String voterName;
    double econPos; // from -10 to 10, -10 is the farthest left and 10 is the farthest right
    double socialPos; // from -10 to 10, -10 is the most libertarian and 10 is the most authoritarian
    ArrayList<Candidate> favoriteCandidates;

    public Voter(){
        Random r = new Random();
        voterName = "Voter #" + r.nextInt(2147483647) + r.nextInt(2147483647);
        econPos = (r.nextDouble() * 20) - 10;
        socialPos = (r.nextDouble() * 20) - 10;
        favoriteCandidates = new ArrayList<Candidate>();
    }

    public Voter(String name, double econPos, double socPos){
        voterName = name;
        this.econPos = econPos;
        socialPos = socPos;
        favoriteCandidates = new ArrayList<Candidate>();
    }

    public double getProximity(double candidateEconPos, double candidateSocPos){ // the lower this number, the more a voter agrees with the given candidate
        double econPosDist = econPos - candidateEconPos;
        double socPosDist = socialPos - candidateSocPos;
        return Math.sqrt((Math.pow(econPosDist,2)) + (Math.pow(socPosDist,2))); // Pythagorean Theorem
    }

    public void findFavCandidates(ArrayList<Candidate> candidates) {
        candidates.sort((c, c2) -> (c.getVoterProximity(econPos, socialPos) >= c2.getVoterProximity(econPos, socialPos) ? 1 : -1));
        favoriteCandidates = candidates;
    }

    public ArrayList<Candidate> getFavCandidates(ArrayList<Candidate> candidates) {
        if (favoriteCandidates.size() == 0) {
            findFavCandidates(candidates);
        }
        return favoriteCandidates;
    }
}
