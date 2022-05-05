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

 
