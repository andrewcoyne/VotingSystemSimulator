import java.util.ArrayList;

public class Party {
    String partyName;
    double money;
    ArrayList<Candidate> candidates;

    public Party(String name, double funds){
        partyName = name;
        money = funds;
        candidates = new ArrayList<Candidate>();
    }

    public void addCandidate(Candidate candidate){
        candidates.add(candidate);
    }
}
