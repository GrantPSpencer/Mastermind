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
 
