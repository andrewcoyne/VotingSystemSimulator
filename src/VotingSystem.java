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

    public VotingSystem (VotingSystem vs) { // for creating copies of each individual voting system
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

    public void updateCondorcet(ArrayList<Candidate> preferredCandidates){ // updates the Condorcet matchups, so the Condorcet winner can be determined
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

    public void vote() { // default voting strategy - the voter just votes for their favorite candidate
        for (int i = 0; i < numVoters; i++) {
            Voter v = new Voter();
            ArrayList<Candidate> preferredCandidates = v.getFavCandidates(electionCandidates);
            preferredCandidates.get(0).voteFor();
            updateCondorcet(preferredCandidates);
            numVotesCast++;
            //System.out.println("(" + v.x + "," + v.y +") voted for (" + preferredCandidates.get(0).x + "," + preferredCandidates.get(0).y + ")");
        }
    }

    public void sortCandidates() { // sorts candidates from most to least votes
        electionCandidates.sort(Comparator.comparingInt(Candidate::getVotes));
        Collections.reverse(electionCandidates);
    }

    public Candidate getCondorcetWinner(){
        condorcetWinner = electionCandidates.get(0);

        for (Candidate c : electionCandidates) {
            int matchupsWon = 0;

            for (ArrayList<Candidate> matchup : condorcetMatchups){
                if (matchup.contains(c)) {
                    int cIndex = matchup.indexOf(c); // index of the given candidate within the matchup
                    int mIndex = condorcetMatchups.indexOf(matchup); // index of the matchup being examined within the ArrayList of all matchups
                    int cScore = condorcetScore.get(mIndex).get(cIndex); // gets the number of people who support the given candidate
                    int oIndex = 2;
                    switch(cIndex){  // gets the index of the other candidate within the matchup
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

            if (matchupsWon == (electionCandidates.size() - 1)){ // if a candidate won every matchup they had, they are the Condorcet winner
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
