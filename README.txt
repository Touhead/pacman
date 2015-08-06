PAC-MAN Online - Version 2.0 - Version Java SE 1.7 - 05/31/2015

GENERAL USAGE NOTES
--------------------

- To run this application, the server needs to meet some specification :
	- The runnable server.jar have to be on the side of a folder name 'maze' which containts the map for the game.
	- Example : ~/myfolder/server.jar and ~/myfolder/maze/

- To create your own maze, follow these rules :

	- You need a fileMyMaze.txt and a fileMyMaze_option.txt.

	- In the fileMyMaze.txt : 
		- '-' : wall
		- '0' : free tile
		- '8' : free tile which contains a point
		- '9' : free tile wich contains an energizer
		- '1' : ghost house tile

	- In the fileMyMaze_option.txt :
		- '-' : wall
		- '0' : free tile wether a point or an energizer or not
		- '3' : PAC-MAN start position
		- '4' : Blinky start position
		- '5' : Pinky start position
		- '6' : Inky start position
		- '7' : Clyde start position

FEATURES
--------------------

- This application is a PAC-MAN like game. You can play it in a 1 player mode or in a multi-player mode.

- For the multi-player, you have to choose a session name. The game starts when 3 clients is connected to the same session.

SERVER FILES
--------------------

AbstractMaze.java
AbstractModel.java
AbstractMultiModel.java
Blinky.java
Clyde.java
DisplayManager.java
DisplayMultiManager.java
EventManager.java
EventMultiManager.java
GameManager.java
GameMultiManager.java
Ghost.java
GhostState.java
Inky.java
Maze.java
MovableObject.java
Move.java
MovementManager.java
MovementMultiManager.java
Object.java
Pacman.java
Pinky.java
PixelPosition.java
Position.java
ResourceManager.java
ResourceMultiManager.java

AbstractController.java
Controller.java

Observable.java
Observer.java

ClientManager.java
EventMultiRunnable.java
EventRunnable.java
IRunnable.java
MoveMultiRunnable.java
MoveRunnable.java
MultiModeRunnable.java
ServerRunnable.java
SoloModeRunnable.java

Client.java

ExceptionInvalidFile.java

Main.java

CLIENT FILES
--------------------

ConnectionWindow.java
ConnectionWindowApplet.java
GameWindow.java
GetFileWindow.java
MainApplet.java
MainApplication.java
MainWindow.java

DisplayRunnable.java
EventRunnable.java
IRunnable.java

Server.java

OTHER FILES
--------------------

server.jar
client.jar

PACMAN_MAZE.txt
PACMAN_MAZE_option.txt
PERSONALIZED_MAZE.txt
PERSONALIZED_MAZE_option.txt


README.txt


