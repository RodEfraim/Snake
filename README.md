# Snake
The program replicates the orginal concept of the game Snake. A game takes place in a boundary,in which the user is allowed to decide how big the game field can be. The purpose is to prevent the snake from going off the canvas and from the snake from eating itself. The goal is for the snake to eat as much food as possible until it fills up the whole entire game field. As the snake eats one piece of food the snake object will gain a SnakeSegment object. 

The Snake object is made up of SnakeSegment objects. The Snake object has primarily two things, the SnakeSegment object head and the SnakeSegment body ArrayList. The GUI's in this program will give the user the opportunity to make a new game whenever they want. The user can also pause the game with the use of the space bar. 

## Getting Started

### Prerequisites

Install the latest version of java. Have all of these files in one folder.

## Running the tests

Compile the java program using the follwing command:

```
javac -cp ./Acme.jar:./objectdraw.jar:. *.java
```

Pass in 3 integer parameters; width, length and delay. Length and width is for the dimensions of the game field. Speed parameter is for the speed of the Snake object. Suggested parameters: 500, 500, 100.

Run the java program using the following command:

```
java -cp ./Acme.jar:./objectdraw.jar:. SnakeController [LENGTH] [WIDTH] [SPEED]

java -cp ./Acme.jar:./objectdraw.jar:. SnakeController 500 500 100
```

### Example Run

Example run demonstration.

<img width="674" alt="screen shot 2019-01-18 at 9 56 10 am" src="https://user-images.githubusercontent.com/32502126/51405426-27edd900-1b0b-11e9-8672-919a42181f0f.png">

<img width="674" alt="screen shot 2019-01-18 at 9 59 00 am" src="https://user-images.githubusercontent.com/32502126/51405434-2f14e700-1b0b-11e9-9ed4-0e09341c7d3c.png">

<img width="674" alt="screen shot 2019-01-18 at 10 01 02 am" src="https://user-images.githubusercontent.com/32502126/51405441-33410480-1b0b-11e9-8b49-82b24ff87775.png">

<img width="674" alt="screen shot 2019-01-18 at 10 08 23 am" src="https://user-images.githubusercontent.com/32502126/51405448-376d2200-1b0b-11e9-84d4-477008edf3af.png">

## Authors

* **Rodrigo Efraim** - *Initial work* - [RodEfraim](https://github.com/RodEfraim)
