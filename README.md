![LOGO](/logo.png?raw=true "logo")

Author: Danny Choi

PvP-based Minecraft plugin where players can choose their species, cast unique special-abilities, engage in indirect combat using passive effects, show off cosmetic effects, and more. Built with Java and [SpongeAPI](https://www.spongepowered.org/).

## Table of Contents

- [Getting Started](#getting-started)
  - [Installation](#installation)
  - [Setup](#setup)
- [How to Play](#how-to-play)
- [Species](#species)
  - [Archer](#archer)
  - [Spacewalker](#spacewalker)
  - [Dog](#dog)
  - [Priest (Coming soon)](#priest-coming-soon)
  
## Getting Started
Setting the plugin up is easy and should take a few minutes at most.

### Installation

To setup the environment for Colosseum, [install a 1.12.2 Minecraft server w/ SpongeAPI](https://docs.spongepowered.org/stable/en/server/index.html) and run it for the first time. Stop the server.

> *Why is it still on Minecraft 1.12.2?* Unfortunately, the plugin cannot be updated until the SpongeAPI is updated to be compatible with the latest Minecraft version.

Download the prebuilt Colosseum JAR file and place it inside the [Your Server]/mods folder. Start the server once again. A message in the console should indicate that Colosseum has successfully loaded and is ready to be used.

### Setup

No further setup is necessary for this plugin.

## How to Play

Each player should select a *species* which also assigns to them their respective *skills*, *passives*, and mechanics of the species. This is done via the player command:

> /species [species name]

A player loads up their *charge* by dealing damage to an enemy player (i.e. hitting and shooting arrows at them). A player's charge level is indicated by the Minecraft client experience bar. When the player has sufficient charge, they can activate their skill by right-clicking a sword and/or left-clicking a bow.  Each species has its own charge-per-hit (CPH), max-charge, charge used upon skill use, and charge needed to activate a skill.

A player will be assigned the passive effect(s) of their respective species. These effects are not directly activated by the player, but are rather triggered by specific game mechanics (e.g. priest getting struck with an arrow).

## Species

### Archer
###### CPH - 25, BOW ONLY
###### Skill(s)
* Explosive Arrow (100 Charge) - Fires an explosive arrow that destroys blocks and deals 6.0 damage to players in a 6 block radius.
###### Passive(s)
* Stealth (14s cooldown) - Upon shooting a player with an arrow, the Archer is granted SPEED 2 and REGEN 1 for 7 seconds.

### Spacewalker
###### CPH - 20
###### Skill(s)
* Warp (100 charge) - Warps to the player the spacewalker is facing with a 25-block maximum reach. The spacewalker is granted SPEED 3 for 5 seconds.
###### Passive(s)
* Oxygen (0s cooldown) - Gives Spacewalker REGEN 1 for 10 seconds upon reaching a charge level of 100.


### Dog
###### CPH - 10
###### Skill(s)
* Bite (100 charge) - Deals 5 damage and SLOWNESS 1 for 2 seconds to all players in a 5-block radius.
###### Passive(s)
* Rabies (0s cooldown) - When the Dog takes damage, there is a 15% chance that it gains STRENGTH 1 for 3 seconds and SPEED 2 for 7 seconds.

### Priest (Coming soon)
###### CPH - 12
###### Skill(s)
###### Passive(s)
