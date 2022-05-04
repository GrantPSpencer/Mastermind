package mastermind.Game;

import java.util.LinkedList;

public class Session {
    

    public Game currentGame;
    public int currentGameSize;
    public boolean currentGameDuplicatesAllowed;
    
    public LinkedList<Integer> winningScoreHistory;
    public LinkedList<Game> gameHistory;

    private CachedPatternGenerator cpg;


    public Session() throws Exception {

        this.winningScoreHistory = new LinkedList<>();
        this.gameHistory = new LinkedList<>();
    
        this.cpg = new CachedPatternGenerator();

    }  


    public void startNewGame(int codeLength, boolean duplicatesAllowed) throws Exception {
        
        //Anytime start new game is called, store the results of the current game into history
            //unless this is the first game played in the session
        if (currentGame != null) {
            storeCurrentGame();
        }


        //Instantiate new game with passed settings and set variables accordingly
        int[] pattern = cpg.getPattern(codeLength, duplicatesAllowed);
        
        this.currentGame = new Game(pattern);
        this.currentGameSize = codeLength;
        this.currentGameDuplicatesAllowed = duplicatesAllowed;



    }

    private void storeCurrentGame() {
        
        //Check if at least 1 guess had been made
        if (currentGame == null || currentGame.remainingGuesses > 9) {
            return;
        }

        //If 1 guess has been made, then store in game history
        gameHistory.add(currentGame);

        //Store the guess counts for any games where user won
        if (currentGame.gameWon) {
            winningScoreHistory.add((10-currentGame.remainingGuesses));
        }
    }



    //Stores the current game's result and then returns session statistics
    public String getEndOfSessionStats() {
        storeCurrentGame();
        return getSessionsStatisticsString();
    }

    //Called when exiting a session, if the user has played at least 1 game, print the 
        //# of games won and played and the average guess count for games won
    //Return empty string if no games played (no stats)
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
