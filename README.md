# Mastermind
Mastermind Game for LinkedIn Reach Coding Challenge

## Installation
Note: This game will likely require java version "16.0.2" or higher to play <br> 



1. Open your terminal and cd into the directory where you want to download the Mastermind folder
2.  In your terminal , type the following and hit enter:
    1. If you do not have git installed on your machine, please refer here: [Git Installation Guide](https://github.com/git-guides/install-git)
```
git clone https://github.com/GrantPSpencer/Mastermind.git
````


3. In your terminal, type the following and hit enter:
```
cd Mastermind
```
4. At this point, you should be in the the Mastermind repository you've just downloaded. Now we will compile all the java files in this directory

5. In your terminal, type the following and hit enter:
    ```
    javac **/*.java -d ./target 
    ```
    1. This will compile all the java files and place their class files into a new directory titled "target"

6. In your terminal, type either of the following and hit enter:
    1. To play with the Java Swing GUI:
    ```
    java -cp ./target mastermind.Main  
    ```
    2. To play directly in the terminal with a text-based UI:
    ```
    java -cp ./target mastermind.ConsoleMain  
    ```
<br>

## Code Structure
Note: The mastermind game files can be found in the Mastermind/src/main/java/mastermind directory. 

The directory is broken down into 3 parts from there:
1. [ConsoleUI](#consoleui) - contains the class responsible for allowing a user to play the game in their terminal
2. [GUI](#gui) - contains all the classes responsible for allowing the user to play the game in a Java Swing GUI
3. [Game](#game) - contains all of the game logic, the pattern generator, and the session class that the GUI and console UI interact with

The files ConsoleMain.java and Main.java are the main files for the terminal and GUI versions of the game, respectively. 

#### ConsoleUI

This subdirectory contains only one class:
* ConsoleUI – This class prints out information from the game to the terminal, prompts user input, and then relays the user input back to the game.  


#### GUI
This subdirectory contains the following classes:
* GameGUI – The primary JFrame that is displayed to the user that contains all of the separate subcomponents.
* GameRows – This is a component within the GameGUI frame, which is comprised of each of the 10 rows that contain the 0-7 dropdown boxes, the colored response squares, and the submit guess button.
* GiveHintPanel – This component contains a JButton and a JLabel. When the button is pressed, a hint is given and it updates the JLabel’s text. 
* SettingsPanel – This component contains 2 user-specified options: “Code Length” and “Duplicates Allowed.” These settings are considered when a new game is created.

#### Game
This subdirectory contains the following classes:
* PatternGenerator – This class is the original pattern generator I created. It makes the API call to random.org and returns an array of digits according to the requested code length and duplicates allowed variables every time a new pattern is requested. 
* CachedPatternGenerator – This class makes the API call to random.org, but makes an initial call of 1000 digits then caches the array and makes an async call to the API whenever another array of 1000 random digits are needed. This is the pattern generator currently being used by the game, but I’ve included the original pattern generator (PatternGenerator) which makes a separate API call for each requested pattern as that seemed to be the intended functionality implied in the challenge document. You can see my comments on the cached pattern generator [here.](#cached-pattern-generator) 
* Game – This class handles all the game logic. Guesses can be passed to the game object and a response array containing the # of bulls and cows will be received. This class only keeps track of current-game variables such as remaining guess count, the code length, and if the game was won.  
* Session – This class handles the starting of new games and storing information regarding previous games. This class also allows us to retrieve statistics regarding the previously played games in a single session. 

<br>
 
## Extensions

#### Difficulty Level Options
1. Code Length 
    1. The user can choose a code length between 4 – 8. 
    2.The higher the number, the more challenging the game will be.
2. Duplicates allowed
    1. The user can choose whether the code will have duplicates or not.
    2. Allowing duplicate numbers in the code may make the game more challenging. 


#### Give Hint Function
1. When playing with the GUI, the user can press the “give hint” button at any time. Likewise, in the terminal UI, the user can type in “hint” as their guess and they will receive a hint from the computer. 
2. The game tries to always give a useful hint. This is done by telling the user the location of a digit that has not already been correctly placed in any guess. 
3. After looping through the digits that have not been correctly placed in any guess, the user will then be given the locations of the digits they correctly placed, in the order that they were placed. 
4. If more hints are given, it simply repeats the hints in the order in which they were revealed. This is done by using an array to track the correctly placed digits and a queue to ensure that the hints are given in a continuous pattern. 
5. While the user should not need more hints than there are digits in the code, you really just never know! 


#### Session Statistics
1. At the end of a game session (both for the GUI and terminal versions), the computer will return the statistics for the games you’ve played. 
    1. A game is considered to have been played if you’ve made at least one guess in it. 
2. The session class tracks the information regarding all the games that have been played and then returns the # of games played, # of games won, and the average amount of guesses it took to win across all the games you won in that session. 

#### Mastermind Bot (Brief)
1. I've also implemented a bot that uses Information Theory to play the mastermind game by interacting directly with the game object. 
2. Please refer to my [Mastermind Bot section](#mastermind-bot) below if you’d like to read more about this. 

<br>

## Mastermind Bot

Note: This bot is very similar to the bot in my [WordleHelper](https://github.com/GrantPSpencer/WordleHelper) project, feel free to check that out as well.<br>I also heavily recommend watching [this video](https://www.youtube.com/watch?v=v68zYyaEmEA) on Information Theory and Wordle by the math youtuber 3Blue1Brown – this video is what inspired me to create my own bot. 
<br>
<br>
Note: The mastermind bot files can be found in the Mastermind/src/main/java/bot directory.

#### How It Works
The bot is given a game with a set code to play and for each turn, makes the best guess given the information it received from the previous guess. Even when a human plays Mastermind, the goal is to use each guess to continuously reduce the possible answers until you’ve narrowed it down to one. This is exactly what the bot does. It uses each guess as an opportunity to narrow down the possible answers until there is only one possible answer left.   


#### Determining the Possible Answer Set
To narrow down the possible answer set, the bot must be able to use the information gained from each guess. The easiest way to do this is to take the result of a guess and then compare that to the result if you had made that same guess against all the possible answers in the answer set. For example, if you receive the information that there are 2 Bulls (correctly placed digit) and 1 Cow (digit in the pattern, but incorrectly placed) in your guess, then the true answer from your possible answer set must return that same count of both bulls and cows. Using this information, any possible answer that would not return the same number of bulls and cows from that guess can be removed from the possible answer set. 

#### Determining the Best Guess
The best guess is the guess that most significantly reduces the possible answer set. For example, if there are currently 10 possible answers and Guess A would reduce the possible answer size to 5 while Guess B would reduce the possible answer size to 3, then Guess B would be considered the better guess. 

We cannot know beforehand the results of the guess (as we do not know the answer code) and how much it would reduce the possible answer size, therefore we cannot know for certain what the best guess is. However, we do know that the answer must be one of the codes still in the possible answer set. Using this information, we loop over the possible answer set and for each possible answer, we guess each possible guess. While it is not illegal to guess a pattern that could not possibly be the correct code, for the bot we consider only the codes in the possible answer set as a possible guess. After guessing each possible guess against all possible answers, we consider how much each guess reduced the possible answer size on average. In other words, how much information did each guess give us on average? The guess that gave us the most information, or reduced the possible answer set size, on average will be the guess that is picked. 


#### Determining the Best <i>First</i> Guess
Determining the best first guess is identical to determining the best n’th guess. However, because the first guess is not dependent on any prior information, once it has been determined, it can simply be hardcoded into our bot. 


#### Analysis of the Best <i>First</i> Guess 

First guess performance analysis for each guess was done by exhaustively iterating over all possible patterns (code set by setter) and recording how many bits of information (i.e., how much it reduces the possible answer set size) the guess gave in each game. Both average bits gained and minimum (“worst case”) bits gained were recorded for all possible guesses. The minimum bits gained by each guess can be used as a max-min approach to break ties where two or more guesses give the same average bits. 

Because the allowed guess set and the possible answer set are identical and are exhaustive list of 0-7 for n columns (n=4 for this analysis), we see a pattern arise in our first guess performance analysis. 

There are 5 different types of first guesses, 
* 4 different numbers (e.g., [1,2,3,4]), 
* 2 same numbers and 2 different numbers (e.g., [1,1,2,3]), 
* 2 same numbers and 2 other same numbers (e.g., [1,1,2,2]), 
* 3 same numbers and 1 different number (e.g., [1,1,1,2]), and, lastly, 
* 4 of the same numbers (e.g., [1,1,1,1]). 

I’ve included a link below to a [Google Spreadsheet](https://docs.google.com/spreadsheets/d/1iNImNiLwGUYSth-Vu3W8HyMPsZ9u5vhhFmBMesS6iNE/edit?usp=sharing) with the sorted data. These patterns have been listed from best to worst in terms of performance, with 4 different numbers yielding the greatest bits on average and in the worst case and with 4 of the same numbers yielding the lowest. The difference between adjacent pattern types is fairly minimal in both average and worst case bits, with the only exception being that the pattern of 4 of the same number scores significantly worse than all other patterns.

#### Preliminary Results
I’ve only run the bot over 1000 possible games, across which it has taken an average of 5.25 guesses to win. Currently, the algorithm is quite slow and needs to be improved if it is to be run over the entire set of 4096 (8^4) possible answers. I discuss this further in the [challenges section.](#mastermind-bot-limitations) 

Current Algorithm's Peformance Over Set of 1000 Possible Codes (length 4, duplicates allowed):
![Mastermind Sim Results](https://user-images.githubusercontent.com/80296166/166864162-5942c7de-c718-4158-bd7f-7436a7de9868.png)

#### Improvements
Currently, the algorithm is quite slow and needs to be improved if it is to be run over the entire set of 4096 (8^4) possible answers. In some cases where the first guess gives little information, the runtime of the algorithm can be quite slow. Because it’s Big O complexity is N^2, if the program has to loop over a set of 1000 possible answers versus a set of 200 possible answers, a SIGNIFICANT difference in the runtime is observed. 

I have attempted to make very minor improvements to the algorithm by eliminating “unnecessary” calculations and reducing the amount of work done at each step. Unfortunately, I do not believe it is possible to make an algorithm that is centered on information theory to be more efficient than O(n^2), though I would love to be corrected on this!

The most obvious answer to reducing the running time of this algorithm would be to simply reduce the possible answer set size. Eliminating duplicates (1680 possible answers) or reducing the pool of potential digits to choose from to 0-5 ( 1296 possible answers) would both have significant impacts on the amount of calculations needed to play a game.  
