import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Voter {
    String voterName;
    double x; // from -10 to 10, -10 is the farthest left and 10 is the farthest right
    double y; // from -10 to 10, -10 is the most libertarian and 10 is the most authoritarian
    ArrayList<Candidate> favoriteCandidates;

    public Voter(){
        Random r = new Random();
        voterName = "Voter #" + r.nextInt(2147483647) + r.nextInt(2147483647);
        x = (r.nextDouble() * 20) - 10;
        y = (r.nextDouble() * 20) - 10;
        favoriteCandidates = new ArrayList<Candidate>();
    }

    public Voter(String name, double econPos, double socPos){
        voterName = name;
        x = econPos;
        y = socPos;
        favoriteCandidates = new ArrayList<Candidate>();
    }

    public String getName(){
        return voterName;
    }

    public double getEconomicPos(){
        return x;
    }

    public double getSocialPos(){
        return y;
    }

    public double getProximity(double candidateEconPos, double candidateSocPos){ // the lower this number, the more a voter agrees with the given candidate
        double econPosDist = x - candidateEconPos;
        double socPosDist = y - candidateSocPos;
        return Math.sqrt((Math.pow(econPosDist,2)) + (Math.pow(socPosDist,2))); // Pythagorean Theorem
    }

    public void findFavCandidates(ArrayList<Candidate> candidates) {
        candidates.sort((c, c2) -> (c.getVoterProximity(x, y) >= c2.getVoterProximity(x, y) ? 1 : -1));
        favoriteCandidates = candidates;
    }

    public ArrayList<Candidate> getFavCandidates(ArrayList<Candidate> candidates) {
        if (favoriteCandidates.size() == 0) {
            findFavCandidates(candidates);
        }
        return favoriteCandidates;
    }
}
