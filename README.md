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
