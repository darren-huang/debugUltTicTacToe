# Debug (Ultimate) TicTacToe
Hello! Welcome to CSM 61b's debugging project. We have coded up TicTacToe with some JUnit 4 unit & integration tests, but we also have snuck in a couple of bugs 🐜! There are some tutorials along the way to help teach how to use Intellij and speed up your debugging process. The solutions for the bugs include how I personally debugged these issues (some of them were real bugs I stumbled upon creating this project).

If you finish debugging the TicTacToe bugs, give debugging our Ultimate TicTacToe project a shot. Ultimate TicTacToe is built upon TicTacToe project from the previous section but has some additional rules. [Here](https://www.youtube.com/watch?v=37PC0bGMiTI&t=103s) is a good YouTube video describing how Ultimate Tic Tac Toe works! Give debugging this bigger project a shot too (though with more code it is much harder).

This is a debugging project meaning, on the git repository I've added different git branches (which you can think of as versions of the code) with bugs added in. Follow the commands to get the bugged versions of the code into your local computer and try debugging them with Intellij!

## Getting Started
1\. clone & enter this repository first! run:
```
git clone https://github.com/darren-huang/debugUltTicTacToe.git
cd debugUltTicTacToe
```
2\. now pull the following branches into your repository. run:
```
git checkout --track origin/UltTicTacToeBug1
git checkout --track origin/TicTacToeBug2
git checkout --track origin/TicTacToeBug1
```
3\. check you are on the `TicTacToeBug1` branch. run:
```
git status
```
- command should print `On branch TicTacToeBug1...`
- if it doesn't print that run `git checkout TicTacToeBug1`

## Q1: Begin Debugging `TicTacToeBug1`!
Start with `TicTacToeBug1` first! This is just the classic Tic Tac Toe game, but the only change is you can now specify
the size of the board and the number of pieces in a row you need to win (so you can play 5x5 tic tac toe etc.).

### Setup Intellij project
We'll be using IntelliJ as our IDE to do our debugging.
1. Open [IntelliJ IDEA](https://www.jetbrains.com/idea/) (install it if you haven't already done so, here is [CS61b's setup guide](https://inst.eecs.berkeley.edu/~cs61b/sp20/materials/lab/lab2/index.html#table-of-contents))
1. go to `File > New > Project From Existing Sources...` and select the `debugUltTicTacToe` folder
1. Press continue through all the menus
1. go to `File > Project Structure > Libraries` and select the `*.jar` files inside `~cs61b-software/lib`
    1. if you are in CS61b and don't have the "cs61b-software" folder
       follow the [Berkeley CS61B instructions](https://inst.eecs.berkeley.edu/~cs61b/sp20/materials/lab/lab2/index.html#table-of-contents) on how to get it.
    1. if you aren't in CS61b use this [link](https://drive.google.com/file/d/1eebQO1WoNoEJ4nxxzhxvHP7Tv7q8EB0R/view) to download & extract the "cs61b-software" folder

### Running the JUnit4 Unit & Integration tests
Before we set off bug hunting lets run the provided tests and see which one(s) fail
1. Open `TicTacToeBoardTest.java` in IntelliJ and look at line 6
1. To the left of `public class TicTacToeBoardTest {` you should see a green arrow. Click it and select `Create 'TicTacToeBoardTest'... > OK` // Alternatively you can also right click the `TicTacToeBoardTest.java` file on the left and select `Create 'TicTacToeBoardTest'... > OK`
1. Now on the toolbar at the top (right below the `File, Edit, View..` row) you should see a green "►" button. Click this to run all the tests. One test should fail!

Here is a quick explanation of what we just did: Every time we click the "►" button we tell IntelliJ to run a "Run Configuration" which tells IntelliJ what code to run and how to run it. If you see the drop-down menu next to the "►" we can select which "Run Configuration" to run. When we right click a file and select the "►" run option, we tell Intellij just to run the main method of that file. 

By going through the steps we did to make a new JUnit Run Configuration, Intellij knows to run all the tests in that file and format the JUnit test output.

### Start Debugging!
Your task is to debug the issue with the code, fix the code, and pass the test! 

#### Quick info about the project:
- The bug is in `TicTacToeBoard.java`, this is where all of the board logic is
    - the most complex function is the "checkWin". This [design document](https://docs.google.com/document/d/17cDhZXbFLUugbCaNCjQap4PMKvjLDWDMUUIfgKW-Aqs/edit?usp=sharing) has info on how it works.
- `TicTacToeBoardTest.java` contains the unit and integration tests. `makeMove()` Test is the integration test.
    - while these unit tests are decent, you should **never** assume a unit test passing implies the function is bug-free
- `TicTacToeGame.java` adds some game logic forcing players to switch off turns and ending the game once someone wins (there shouldn't be any bugs here)
    - to play 3 x 3 version of tic tac toe run the main method of this file.

#### Debugging Tips
1. First step is to understand how the code works. Without a semi-decent understanding of how the code works, debugging can be almost impossible. Luckily in 61b projects you are debugging code you mostly wrote yourself, and luckily again this TicTacToe project isn't too complex. Here are some steps to get acquainted in this project.
    1. Use the ["design document" on this project](https://docs.google.com/document/d/17cDhZXbFLUugbCaNCjQap4PMKvjLDWDMUUIfgKW-Aqs/edit?usp=sharing)
       in order to get a better idea of what the code is doing.
1. Dive into the code on IntelliJ. Use some of these IntelliJ features to help you navigate/explore the code:
    1. **`Cmd Shift -`** will minimize all the code bodies to just see method signatures and associated comments. 
        1. Windows users replace `Cmd` with `Ctrl`.
        1. Expand the minimized stuff with `Cmd Shift +`. 
        1. to expand only the method your cursor is on, use `Cmd Opt +`, Windows `Ctrl Alt +`
        1. This is called Code Folding if you wanna google extra IntelliJ commands for it.
    1. **hold `Cmd` and click** the name of class/function/variable to move to the location in the code where it is defined
        1. Windows ppl use `Ctrl` click
        1. there are usually comments near where the class/function/variable was defined that say what it is used for so this is helpful for when you don't know what a variable/function is used for
    1. **right click and select `Find Usages`** on any class/function/variable to see where it is used in the code 
        1. if you are already at the class/function/variable, a `Cmd` or `Ctrl` click will also display the usage locations
    1. `Cmd Opt left` will bring you back to your last cursor position in the project 
        1. `Cmd Opt right` will bring you to the next most recent cursor position
        1. If you `Cmd` or `Ctrl` clicked many times in a row and got lost on where you were originally, use this command to get back on track 
        1. for Windows users this is `Ctrl Alt left` and `Ctrl Alt right`, **WARNING:** if this messes up your display fix with `Ctrl Alt up`, follow [these instructions](https://superuser.com/questions/373832/disable-alt-arrow-display-flip-keyboard-shortcut) to prevent the command from messing up your display
    
1. Using the Debugger! This helps you by walking through the key parts of the code where stuff might be going wrong. We're assuming you know how to get into debugging mode by setting break points and hitting the 🐞 debug button at the top. Here are some more important debugging buttons: ![IntelliJ Debugging Window Img](imgs/debugging_intellij.PNG)
    1. [Red] Step Over: Run one line of code in the current function call (equivalent of moving exactly one line down)
    1. [Yellow] Step Into: Go to the first line of the function that is being called (ie. if currently on a line `student.debug("tictactoe")` it will go to the first line of the `debug` function in the `student`'s class) 
        1. If there are multiple functions to step into, you can left click which one you want to step into. (ie. if running `student.debug(project.getName())` click the [Yellow] button and then left click either `debug` or `getName`)
    1. [Green] Step Out: keep running the current function until a return or the end of the function and return to the place in code where the current function was called. Steps you out of the current call frame.
    1. **_[Blue] Continue:_** run the code normally (ie. infinitely click "Step Over") until you hit a break point. This allows you to set a lot of break points at points of interest and fast forward in-between the break points you set rather than clicking "Step Over" 120312+ times.
    1. [Purple] Mute Breakpoints: prevents code from stopping at any break points. This is useful when you have breakpoints in frequently used functions and want to "Step Over" a bunch of code without stopping on break points.
    1. [Pink] Show Execution Point: if you get lost scrolling through other sections of code and forget where the current debugger is stopped at, this brings you back to where the debugger is
    
## Q2: TicTacToeBug2

If you're done move on to `TicTacToeBug2` which passes the current test but will fail a new integration test in `TicTacToeBoardTest`. Note that this one passes all unit tests, but the unit tests here missed something which is affecting the integration test. One debugging idea is to make the unit tests more robust to catch the bug, but in my personal opinion debugging the integration test is far easier.

## Q3: UltTicTacToeBug1

If you're done move on to `UltTicTacToeBug1` which passes  `TicTacToeBoardTest` but will fail a test in `UltBoardTest`.

## Switching between problems
currently there are 3 different bugged branches of the code. One is named UltTicTacToeBug1 and the other two are named TicTacToeBug1 & TicTacToeBug2
to switch in-between them, commit all your changes first then run
```
git checkout TicTacToeBug1
git checkout TicTacToeBug2
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