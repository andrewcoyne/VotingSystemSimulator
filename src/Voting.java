import java.util.ArrayList;

public class Voting {
    public static void main(String[] args){
        System.out.println("Running voting system simulations...");
        simulateRandom("plurality",500,500000,4, false);
    }

    private synchronized static void simulate(String votingSystem, int electionsToSimulate, int numOfVoters, ArrayList<Candidate> candidates, boolean printElectionResults){
        final int[] condorcetWins = {0,0};
        for (int i = 1; i <= electionsToSimulate; i++) {
            Thread thread = new Thread(""+i) {
                public synchronized void run(){

                    VotingSystem v = new VotingSystem(candidates, numOfVoters);
                    switch(votingSystem){
                        case "plurality": v = new Plurality(candidates, numOfVoters); break;
                        case "ranked-choice": v = new RankedChoice(candidates, numOfVoters); break;
                        case "copeland-borda": v = new CopelandBorda(candidates, numOfVoters); break;
                        default: break;
                    }

                    ArrayList<Candidate> results = v.getElectionResults();
                    Candidate condorcetWinner = v.getCondorcetWinner();
                    if (condorcetWinner.equals(results.get(0))) {
                        condorcetWins[0]++;
                    }

                    if(printElectionResults){
                        printResults(results, condorcetWinner, numOfVoters);
                    }

                    condorcetWins[1]++;
                }
            };
            thread.start();
        }

        boolean wait = true;
        while(wait){
            StringBuilder str = new StringBuilder(140);
            str.append("\r").append(condorcetWins[1]).append("/").append(electionsToSimulate).append(" election(s) simulated");
            System.out.print(str);
            if(condorcetWins[1] == electionsToSimulate){
                str.append("\r").append(electionsToSimulate).append("/").append(electionsToSimulate).append(" election(s) simulated");
                System.out.print(str);
                System.out.println("");
                System.out.println("The Condorcet Winner won the election " + ((condorcetWins[0] /(double)electionsToSimulate))*100 + "% of the time.");
                wait = false;
            }
        }
    }

    private synchronized static void simulateRandom(String votingSystem, int electionsToSimulate, int numOfVoters, int numOfCandidates, boolean printElectionResults){
        final int[] condorcetWins = {0,0};
        for (int i = 1; i <= electionsToSimulate; i++) {
            Thread thread = new Thread(""+i) {
                public synchronized void run(){
                    ArrayList<Candidate> candidates = new ArrayList<Candidate>();
                    for (int j = 0; j < numOfCandidates; j++) {
                        Candidate c = new Candidate();
                        candidates.add(c);
                    }

                    VotingSystem v = new VotingSystem(candidates, numOfVoters);
                    switch(votingSystem){
                        case "plurality": v = new Plurality(candidates, numOfVoters); break;
                        case "ranked-choice": v = new RankedChoice(candidates, numOfVoters); break;
                        case "copeland-borda": v = new CopelandBorda(candidates, numOfVoters); break;
                        default: break;
                    }

                    ArrayList<Candidate> results = v.getElectionResults();
                    Candidate condorcetWinner = v.getCondorcetWinner();
                    if (condorcetWinner.equals(results.get(0))) {
                        condorcetWins[0]++;
                    }

                    if(printElectionResults){
                        printResults(results, condorcetWinner, numOfVoters);
                    }

                    condorcetWins[1]++;
                }
            };
            thread.start();
        }

        boolean wait = true;
        while(wait){
            StringBuilder str = new StringBuilder(140);
            str.append("\r").append(condorcetWins[1]).append("/").append(electionsToSimulate).append(" election(s) simulated");
            System.out.print(str);
            if(condorcetWins[1] == electionsToSimulate){
                str.append("\r").append(electionsToSimulate).append("/").append(electionsToSimulate).append(" election(s) simulated");
                System.out.print(str);
                System.out.println("");
                System.out.println("The Condorcet Winner won the election " + ((condorcetWins[0] /(double)electionsToSimulate))*100 + "% of the time.");
                wait = false;
            }
        }
    }

    private static void printResults(ArrayList<Candidate> results, Candidate condorcetWinner, int numOfVoters){
        System.out.println("Condorcet Winner: " + condorcetWinner.candidateName);
        for(Candidate c : results) {
            System.out.println(c.candidateName + " (" + ((double)Math.round(c.x*100)/100) +
                    "," + ((double)Math.round(c.y*100)/100) + "): " + c.getVotes() +
                    " votes (" + Math.round((c.getVotes() / (double) numOfVoters) * 100) + "%)");
        }
    }
}

