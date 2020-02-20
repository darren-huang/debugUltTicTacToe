# debugUltTicTacToe
An Ultimate Tic Tac Toe Project, complete with an interactive game, JUnit4 testing in the form of both unit tests and 
integration tests. [Here](https://www.youtube.com/watch?v=37PC0bGMiTI&t=103s) is a good YouTube video describing how 
Ultimate Tic Tac Toe works! This is a debugging project meaning, on the git repository I've added different git branches 
(which you can think of as versions of the code) with bugs added in. Follow the commands to get the bugged versions of
the code into your local computer and try debugging them with Intellij!

## Getting Started
clone & enter this repository first!
```
git clone https://github.com/darren-huang/debugUltTicTacToe.git
cd debugUltTicTacToe
```
now pull the following branches into your repository
```
git checkout --track origin/UltTicTacToeBug1
git checkout --track origin/TicTacToeBug1
```
## Switching between problems
currently there are 2 different bugged branches of the code. One is named UltTicTacToeBug1 and the other is named TicTacToeBug1
to switch in-between them, commit all your changes first then run
```
git checkout TicTacToeBug1
git checkout UltTicTacToeBug1
```
## Begin Debugging!
Start with `TicTacToeBug1` first! This is just the classic Tic Tac Toe game, but the only change is you can now specify
the size of the board and the number of pieces in a row you need to win (so you can play 5x5 tic tac toe etc.). 

Open the folder in IntelliJ by opening Intellij and going to `File > New > Project From Existing Sources...` and selecting the 
debugUltTicTacToe folder. Press continue through all the menus. Next remember
to go to `File > Project Structure > Libraries` and select the `*jar` files inside `~cs61b-software/lib` if you don't have that
follow the [Berkeley CS61B instructions](https://inst.eecs.berkeley.edu/~cs61b/sp20/materials/lab/lab2/index.html#table-of-contents) on how to get it.

If everything is setup correctly (and you've added the library files) you should be able to run the tests in `TicTacToeBoardTest.java`.
Next to `public class TicTacToeBoardTest {` on line 6, you should see a green arrow. Click it and select `Create 'TicTacToeBoardTest'... > OK`. 
Finally hit the green play button at the top of the tool bar to run the `TicTacToeBoardTest` test that we just created. A test 
should fail. Your task is to debug the issue with the code, fix the code, and pass the test! Use this 
[Design Document/Notes on this project](https://docs.google.com/document/d/17cDhZXbFLUugbCaNCjQap4PMKvjLDWDMUUIfgKW-Aqs/edit?usp=sharing)
in order to get a better idea of what the code is doing.

If you're done move on to `UltTicTacToeBug1` which passes  `TicTacToeBoardTest` but will fail a test in `UltBoardTest`.

Here are some useful links!
- [Design Document/Notes on this project](https://docs.google.com/document/d/17cDhZXbFLUugbCaNCjQap4PMKvjLDWDMUUIfgKW-Aqs/edit?usp=sharing)
- [Intellij/Debugging/Testing Tips](https://docs.google.com/document/d/17ugWs_ipBBRlZshpwPPjHoRLSLhtmohu6Rz8wQAZR7M/edit?usp=sharing)
- [Solutions to the Debugging Problems (SPOILER)](https://docs.google.com/document/d/1opfU843rqPHkn_jHFXkbDJFtDu_oUqbxtuVcqn9f3d4/edit?usp=sharing)

Make sure to read the Design Document to get an understanding of how the code in this project
works. There a couple of tips and tricks provided for using Intellij, Debugging with Intellij, and creating unit Tests in
Intellij. (this project only expects you to debug, not write tests, but I used the Testing tips myself to create the tests
you see here)