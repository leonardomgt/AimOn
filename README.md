# AimOn
LPOO Final Project

## Package Diagram

![alt text](https://github.com/leonardomgt/AimOn/blob/master/diagrams/package_diagram.png "Package Diagram")

## Class Diagram

![alt text](https://github.com/leonardomgt/AimOn/blob/master/diagrams/class_diagram.png "Class Diagram")

### Class List

* AimOn

Aggregator Class. Responsible for creating the game, render the screens and dispose assets.

* MainMenuScreen

Class responsible for rendering the initial menu on the screen. From this screen, the user will be redirected to the game screen.

* GameScreen

Class responsible for rendering the game screen.

* EntityView

Abstract class responsible for rendering specific objects on the screen.

* DuckView

Implementation of EntityView class for rendering ducks on the screen.

* MainModel

Class to store the game state, the data held by this class is used by game screen to draw the world state.

* EntityModel

Abstract class to store the model of each actor of the game (duck, gun, etc)

* GroundModel

Implementation of EntityModel class to store the model of the ground

* DuckModel

Implementation of EntityModel class to store the duck model

* MainController

Class to update the physics of the game (kill ducks, respawn ducks, fly ducks, fire weapon, etc)

* EntityBody

Abstract class to control the physics of the bodies in the game

* GroundBody

Implementation of EntityBody class to control the physics of the ground

* DuckBody

Implementation of EntityBody class to control the physics of ducks

### Print Screens

* Flying Duck

![alt text](https://github.com/leonardomgt/AimOn/blob/master/diagrams/flying_duck.png "Flying Duck")

* Falling Duck

![alt text](https://github.com/leonardomgt/AimOn/blob/master/diagrams/falling_duck.png "Falling Duck")


### Unit Tests List

## View

	* Test input handles like zoom changes, keys, mouse scroll, etc.

## Model

  * Test if AimModel position matches the mouse movements
	* Test if the target (ducks) number is the same of the GameModel constructor.
	* Test if the program interrupts when AimModel leaves the world limits.
	* Assert that all the strategies of the targets are implemented (flying routes, speed, etc)


## Controller

	* Test if the ducks fall onto the ground upon being shot
	* Test if the ducks don't fly over the upper limit of the world
	* Test if the target is hit and killed when the mouse is clicked and the aim is on the target
	* Test if target keeps alive after a failed shot
	* Test if the game ends when all targets are shot
