# Snake
The program replicates the orginal concept of the game Snake. A game takes place in a boundary,in which the user is allowed to decide how big the game field can be. The purpose is to prevent the snake from going off the canvas and from the snake from eating itself. The goal is for the snake to eat as much food as possible until it fills up the whole entire game field. As the snake eats one piece of food the snake object will gain a SnakeSegment object. 

The Snake object is made up of SnakeSegment objects. The Snake object has primarily two things, the SnakeSegment object head and the SnakeSegment body ArrayList. The GUI's in this program will give the user the opportunity to make a new game whenever they want. The user can also pause the game with the use of the space bar. 

## Getting Started

### Prerequisites

Install the latest version of java. Have all of these files in one folder.

## Running the tests

Compile the java program

```
javac -cp ./Acme.jar:./objectdraw.jar:. *.java
```

Run java program. Pass in 3 integer parameters; length, width and speed. Length and width is for the dimensions of the game field. Speed parameter is for the speed of the Snake object. Suggested parameters: 500, 500, 100.

```
java -cp ./Acme.jar:./objectdraw.jar:. SnakeController [LENGTH] [WIDTH] [SPEED]

java -cp ./Acme.jar:./objectdraw.jar:. SnakeController 500 500 100
```

### Example Run

Example run demonstration.


## Authors

* **Rodrigo Efraim** - *Initial work* - [RodEfraim](https://github.com/RodEfraim)
