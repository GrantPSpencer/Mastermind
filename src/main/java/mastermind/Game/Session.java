package mastermind.Game;

import java.util.LinkedList;

public class Session {
    

    public Game currentGame;
    public int currentGameSize;
    public boolean currentGameDuplicatesAllowed;
    
    private long sessionStartTime;
    public LinkedList<Integer> winningScoreHistory;
    public LinkedList<Game> gameHistory;

    //TODO patternGenerator
    CachedPatternGenerator cpg;

    public Session() throws Exception {

        this.sessionStartTime = System.currentTimeMillis();

        this.winningScoreHistory = new LinkedList<>();
        this.gameHistory = new LinkedList<>();
    
        this.cpg = new CachedPatternGenerator();

        // this.currentGame = new Game(cpg.getPattern(4, true));
        // this.currentGameSize = 4;
        // this.currentGameDuplicatesAllowed = true;
    }  


    public void startNewGame(int codeLength, boolean duplicatesAllowed) throws Exception {
        
        if (currentGame != null) {
            storeCurrentGame();
        }

        int[] pattern = cpg.getPattern(codeLength, duplicatesAllowed);

        
        this.currentGame = new Game(pattern);
        this.currentGameSize = codeLength;
        this.currentGameDuplicatesAllowed = duplicatesAllowed;



    }

    private void storeCurrentGame() {
        
        //Check if at least 1 guess had been made
        if (currentGame.remainingGuesses > 9) {
            return;
        }

        gameHistory.add(currentGame);

        if (currentGame.gameWon) {
            winningScoreHistory.add((10-currentGame.remainingGuesses));
        }
    }

    public String getEndOfSessionStats() {
        storeCurrentGame();
        return getSessionsStatisticsString();
    }

    private String getSessionsStatisticsString() {

        String statsString = "";
        if (gameHistory.size() > 0) {
            if (winningScoreHistory.size() == 0) {
                statsString = "You won 0 of the " + gameHistory.size() + " games you played.";
                
            } else {
                int winningScoreSum = 0;
                for (int score : winningScoreHistory) {
                    winningScoreSum += score;
                }
                statsString = "You won " + winningScoreHistory.size() + " of the " + gameHistory.size() + " games you played, with an average winning guess count of " 
                + ((double)winningScoreSum/(double)winningScoreHistory.size()) + " guesses.";
            }
        }

        return statsString;
    } 



}
