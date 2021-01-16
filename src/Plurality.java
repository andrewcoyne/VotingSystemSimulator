import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Plurality extends VotingSystem {
    public Plurality(ArrayList<Candidate> candidates, int numOfVoters) {
        super(candidates, numOfVoters);
    }

    public void vote() {
        Random r = new Random();

        for (int i = 0; i < numVoters; i++) {
            Voter v = new Voter();
            ArrayList<Candidate> preferredCandidates = v.getFavCandidates(electionCandidates);

            if(numVotesCast > (preferredCandidates.size() * 5)) {
                int j = r.nextInt(100); // account for third-party voters
                if(j > 2) {
                    //sortCandidates();
                    if(electionCandidates.get(0).getVoterProximity(v.x,v.y) < electionCandidates.get(1).getVoterProximity(v.x,v.y)){
                        electionCandidates.get(0).voteFor();
                    }else{
                        electionCandidates.get(1).voteFor();
                    }
                }else{
                    preferredCandidates.get(0).voteFor();
                }
            }else{
                preferredCandidates.get(0).voteFor();
                sortCandidates();
            }

            updateCondorcet(preferredCandidates);
            numVotesCast++;
            //System.out.println("(" + v.x + "," + v.y +") voted for (" + preferredCandidates.get(0).x + "," + preferredCandidates.get(0).y + ")");
        }
    }
}
