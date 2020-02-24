# Debug (Ultimate) TicTacToe
Hello! Welcome to CSM 61b's debugging project. We have coded up TicTacToe with some JUnit 4 unit & integration tests, but we also have snuck in a couple of bugs 🐜! There are some tutorials along the way to help teach how to use Intellij and speed up your debugging process. The solutions for the bugs include how I personally debugged these issues (some of them were real bugs I stumbled upon creating this project).

If you finish debugging the TicTacToe bugs give debugging our Ultimate TicTacToe project a shot. Ultimate TicTacToe is built upon TicTacToe project from the previous section but has some additional rules. [Here](https://www.youtube.com/watch?v=37PC0bGMiTI&t=103s) is a good YouTube video describing how Ultimate Tic Tac Toe works! Give debugging this bigger project a shot too (though with more code it is much harder).

This is a debugging project meaning, on the git repository I've added different git branches (which you can think of as versions of the code) with bugs added in. Follow the commands to get the bugged versions of the code into your local computer and try debugging them with Intellij!

## Getting Started
1\. clone & enter this repository first!
```
git clone https://github.com/darren-huang/debugUltTicTacToe.git
cd debugUltTicTacToe
```
2\. now pull the following branches into your repository
```
git checkout --track origin/UltTicTacToeBug1
git checkout --track origin/TicTacToeBug1
```
3\. check you are on the `TicTacToeBug1` branch
```
git status
```
- command should print `On branch TicTacToeBug1...`
- if it doesn't print that run `git checkout TicTacToeBug1`

## Begin Debugging `TicTacToeBug1`!
Start with `TicTacToeBug1` first! This is just the classic Tic Tac Toe game, but the only change is you can now specify
the size of the board and the number of pieces in a row you need to win (so you can play 5x5 tic tac toe etc.).

### Setup Intellij project
We'll be using IntelliJ as our IDE to do our debugging.
1. Open [IntelliJ IDEA](https://www.jetbrains.com/idea/) (install it if you haven't already done so, here is [CS61b's setup guide](https://inst.eecs.berkeley.edu/~cs61b/sp20/materials/lab/lab2/index.html#table-of-contents))
1. go to `File > New > Project From Existing Sources...` and select the `debugUltTicTacToe` folder
1. Press continue through all the menus
1. go to `File > Project Structure > Libraries` and select the `*jar` files inside `~cs61b-software/lib`
    1. if you are in CS61b and don't have the "cs61b-software" folder
       follow the [Berkeley CS61B instructions](https://inst.eecs.berkeley.edu/~cs61b/sp20/materials/lab/lab2/index.html#table-of-contents) on how to get it.
    1. if you aren't in CS61b use this [link](https://drive.google.com/file/d/1eebQO1WoNoEJ4nxxzhxvHP7Tv7q8EB0R/view) to download & extract the "cs61b-software" folder

### Running the JUnit4 Unit & Integration tests
Before we set off bug hunting lets run the provided tests and see which one(s) fail
1. Open `TicTacToeBoardTest.java` in IntelliJ and look at line 6
1. To the left of `public class TicTacToeBoardTest {` you should see a green arrow. Click it and select `Create 'TicTacToeBoardTest'... > OK`
1. Now on the toolbar at the top (right below the `File, Edit, View..` row) you should see a green "►" button. Click this to run all the tests. One test should fail!

Here is a quick explanation of what we just did: Every time we click the "►" button we tell IntelliJ to run a "Run Configuration" if you see the drop-down menu next to the "►" we can select which "Run Configuration" to run. 


A test 
should fail. Your task is to debug the issue with the code, fix the code, and pass the test! Use this 
[Design Document/Notes on this project](https://docs.google.com/document/d/17cDhZXbFLUugbCaNCjQap4PMKvjLDWDMUUIfgKW-Aqs/edit?usp=sharing)
in order to get a better idea of what the code is doing.

If you're done move on to `UltTicTacToeBug1` which passes  `TicTacToeBoardTest` but will fail a test in `UltBoardTest`.

## Switching between problems
currently there are 2 different bugged branches of the code. One is named UltTicTacToeBug1 and the other is named TicTacToeBug1
to switch in-between them, commit all your changes first then run
```
git checkout TicTacToeBug1
git checkout UltTicTacToeBug1
```

## Link Compilation!
- [Design Document/Notes on this project](https://docs.google.com/document/d/17cDhZXbFLUugbCaNCjQap4PMKvjLDWDMUUIfgKW-Aqs/edit?usp=sharing)
- [Intellij/Debugging/Testing Tips](https://docs.google.com/document/d/17ugWs_ipBBRlZshpwPPjHoRLSLhtmohu6Rz8wQAZR7M/edit?usp=sharing)
- [Solutions to the Debugging Problems (SPOILER)](https://docs.google.com/document/d/1opfU843rqPHkn_jHFXkbDJFtDu_oUqbxtuVcqn9f3d4/edit?usp=sharing)

Make sure to read the Design Document to get an understanding of how the code in this project
works. There a couple of tips and tricks provided for using Intellij, Debugging with Intellij, and creating unit Tests in
Intellij. (this project only expects you to debug, not write tests, but I used the Testing tips myself to create the tests
you see here)

## Solutions

[Solutions to the Debugging Problems (SPOILER)](https://docs.google.com/document/d/1opfU843rqPHkn_jHFXkbDJFtDu_oUqbxtuVcqn9f3d4/edit?usp=sharing)

You can also get bug-free versions of the code with the following commands
```
git checkout --track origin/UltTicTacToe
git checkout --track origin/TicTacToe
```
you can move between these branches by checking them out, just remove the `--track origin/` from the command after the
first time you run them