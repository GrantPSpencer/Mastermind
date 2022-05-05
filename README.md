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

#### Mastermind Bot
1. I've also implemented a bot that uses Information Theory to play the mastermind game by interacting directly with the game object. 
2. Please refer to my [Mastermind Bot section](#mastermind-bot) if you’d like to read more about this. 

