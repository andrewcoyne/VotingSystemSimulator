import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class VotingSystem {

    ArrayList<Candidate> electionCandidates;
    int numVoters;
    int numVotesCast;
    Candidate condorcetWinner;
    ArrayList<ArrayList<Candidate>> condorcetMatchups;
    ArrayList<ArrayList<Integer>> condorcetScore;

    public VotingSystem (ArrayList<Candidate> candidates, int numOfVoters) {
        electionCandidates = candidates;
        numVoters = numOfVoters;
        generateCondorcetMatchups(candidates);
    }

    public VotingSystem (VotingSystem vs) {
        electionCandidates = vs.electionCandidates;
        numVoters = vs.numVoters;
        numVotesCast = vs.numVotesCast;
        condorcetWinner = vs.condorcetWinner;
        condorcetMatchups = vs.condorcetMatchups;
        condorcetScore = vs.condorcetScore;
    }

    public void generateCondorcetMatchups (ArrayList<Candidate> candidates){
        condorcetMatchups = new ArrayList<ArrayList<Candidate>>();
        condorcetScore = new ArrayList<ArrayList<Integer>>();

        for (Candidate c : candidates) {
            for (Candidate c2 : candidates) {
                if (!c.equals(c2)) {
                    ArrayList<Candidate> matchup = new ArrayList<Candidate>();
                    matchup.add(c);
                    matchup.add(c2);

                    ArrayList<Candidate> matchupFlip = new ArrayList<Candidate>();
                    matchupFlip.add(c2);
                    matchupFlip.add(c);

                    if(!condorcetMatchups.contains(matchup) && !condorcetMatchups.contains(matchupFlip)){
                        condorcetMatchups.add(matchup);

                        ArrayList<Integer> score = new ArrayList<Integer>();
                        score.add(0);
                        score.add(0);
                        condorcetScore.add(score);
                    }
                }
            }
        }
    }

    public void updateCondorcet(ArrayList<Candidate> preferredCandidates){
        for (ArrayList<Integer> matchupScoreSet : condorcetScore){
            int index = condorcetScore.indexOf(matchupScoreSet);
            ArrayList<Candidate> matchup = condorcetMatchups.get(index);
            if(preferredCandidates.indexOf(matchup.get(0)) < preferredCandidates.indexOf(matchup.get(1))){
                matchupScoreSet.set(0, matchupScoreSet.get(0) + 1);
            }else{
                matchupScoreSet.set(1, matchupScoreSet.get(1) + 1);
            }
        }
    }

    public void vote() {
        for (int i = 0; i < numVoters; i++) {
            Voter v = new Voter();
            ArrayList<Candidate> preferredCandidates = v.getFavCandidates(electionCandidates);
            preferredCandidates.get(0).voteFor();
            updateCondorcet(preferredCandidates);
            numVotesCast++;
            //System.out.println("(" + v.x + "," + v.y +") voted for (" + preferredCandidates.get(0).x + "," + preferredCandidates.get(0).y + ")");
        }
    }

    public void sortCandidates() {
        electionCandidates.sort(Comparator.comparingInt(Candidate::getVotes));
        Collections.reverse(electionCandidates);
    }

    public Candidate getCondorcetWinner(){
        condorcetWinner = electionCandidates.get(0);

        for (Candidate c : electionCandidates) {
            int matchupsWon = 0;

            for (ArrayList<Candidate> matchup : condorcetMatchups){
                if (matchup.contains(c)) {
                    int cIndex = matchup.indexOf(c);
                    int mIndex = condorcetMatchups.indexOf(matchup);
                    int cScore = condorcetScore.get(mIndex).get(cIndex);
                    int oIndex = 2;
                    switch(cIndex){
                        case 0: oIndex = 1; break;
                        case 1: oIndex = 0; break;
                    }
                    int oScore = condorcetScore.get(mIndex).get(oIndex);

                    if (cScore > oScore){
                        matchupsWon++;
                    }else{
                        break;
                    }
                }
            }

            if (matchupsWon == (electionCandidates.size() - 1)){
                condorcetWinner = c;
                //System.out.println("Condorcet winner found!");
            }
        }

        return condorcetWinner;
    }

    public ArrayList<Candidate> getElectionResults() {
        vote();
        sortCandidates();
        return electionCandidates;
    }
}
