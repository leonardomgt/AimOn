# AimOn
LPOO Final Project

## Package Diagram

![alt text](https://github.com/leonardomgt/AimOn/blob/master/diagrams/package_diagram.png "Package Diagram")

## Class Diagram

![alt text](https://github.com/leonardomgt/AimOn/blob/master/diagrams/class_diagram.png "Class Diagram")

### Class List

* AimOn

Aggregator Class. Responsible for creating the game.

* MainMenuScreen

Class responsible for rendering the initial menu on the screen. From this screen, the user will be redirected to the game screen.

* GameScreen

Class responsible for rendering the game screen.

* EntityView

Abstract class responsible for rendering specific objects on the screen.

* DuckView

Implementation of EntityView class for rendering ducks on the screen.

* GameStatusView

Class to render several sprites on the screen with game status data

* MainModel

Class to store the game state, the data held by this class is used by game screen to draw the world state.

* EntityModel

Abstract class to store the model of each actor of the game (duck, gun, etc)

* GroundModel

Implementation of EntityModel class to store the model of the ground

* DuckModel

Implementation of EntityModel class to store the duck model

* PlayerModel

Implementation of EntityModel class to store the player model

* GunModel

Implementation of EntityModel class to store the gun model

* MainController

Class to update the physics of the game (kill ducks, respawn ducks, fly ducks, fire weapon, etc)

* EntityBody

Abstract class to control the physics of the bodies in the game

* GroundBody

Implementation of EntityBody class to control the physics of the ground

* DuckBody

Implementation of EntityBody class to control the physics of ducks

* PlayerController

Class to update the logic of the player

* PlayerController

Class to update the logic of the player gun

* DuckBehaviour

Abstract class of a duck behaviour. 

* DeweyBehaviour

Implementation of DuckBehaviour class to control the behaviour of dewey ducks

* HueyBehaviour

Implementation of DuckBehaviour class to control the behaviour of huey ducks

* LouieBehaviour

Implementation of DuckBehaviour class to control the behaviour of louie ducks

* GameInputProcessor

Abstract class that extends InputAdapter of Libgdx library
 
* AndroidInputProcessor

Implementation of GameInputProcessor to process android input

* AndroidInputProcessor

Implementation of GameInputProcessor to process desktop input


### Print Screens

* Flying Duck

![alt text](https://github.com/leonardomgt/AimOn/blob/master/diagrams/flying_duck.png "Flying Duck")

* Falling Duck

![alt text](https://github.com/leonardomgt/AimOn/blob/master/diagrams/falling_duck.png "Falling Duck")


### Unit Tests List

## General

* Aim Location
* Associate Duck Model with its Duck Body  
* Game Over 
* Generate Main Model
* Next Level

## Ducks

* Change direction
* Duck behaviours
* Duck moves
* Duck falling and dying
* Ducks born alive
* Frighten duck from front
* Frighten duck from behind
* Frighten duck change velocity
* Duck frightened time
* Frighten duck already frightened
* Kill duck
* Send duck down
* Send duck up

## Gun

* Gun empty round
* Reload ammo
* Reload empty player ammo
* Reload while firing
* Reload when already loading

### Design Patterns

* Singleton: the ground body and model are singletons
* Strategy: each duck has his own strategy. A little variation from the pure strategy design. Instead of Interface, there is an abstract class.

### Requirements Covered

* Physics
* Artificial Intelligence (Ducks get scared)
* Mobile

### Game Rules

Aim the duck, press mouse left button to shoot and scroll (or right button) to zoom. Reload with R. 
Have fun and kill those bastards. 