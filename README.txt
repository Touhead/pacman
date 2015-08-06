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


