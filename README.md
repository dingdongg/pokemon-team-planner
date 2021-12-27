# Pokemon Team Planner


***Project description***

THis is a program that allows users to build their
own collection of Pokemon teams. It allows users to edit, create, and
store a collection of Pokemon team ideas. This program also takes the pokemons' 
types into consideration and give feedback on the strengths and weaknesses of the team. 
Hence, it is a useful tool for anyone looking to plan out teams in their playthrough of 
Pokemon games, for both casual and competitive players alike.

The inspiration for this project stems from my love for pokemon games.
Even to this day, I would spend most of my free time replaying these
same games because to me, they are classics. My favorite part with each playthrough
is the part where I sit down and plan out a brand new pokemon team. This often took
up a long time because I always wanted my team to be as strong as it could be,
without having too many weaknesses. And because I don't have much spare time in
university, I never ended up finishing my teams and never got to replay the game.
By creating this program, I am hoping that my team planning process will become a lot
shorter, so that I will be able to actually play through the game with my pokemon team.

---

## Features

- Create a new pokemon having: a name, and 1/2 pokemon type(s)
- Design a team of pokemons and add it to a collection of teams
- Edit/delete existing pokemon team(s)
- View the contents of a selected team, showing the pokemons' names and types
- Save/load team collection to/from JSON file

---

## Installing

1. Clone this repo (preferrably in IntelliJ)
2. Navigate to main/src/ui/Main class
3. Right click the Main class and click "Run"

***NOTE: *** There is a GUI rendering issue when the program is run another application that isn't IntelliJ. 
This is currently being fixed; apologies for any inconveniences caused.

___

***Project background explanation***

Here, I will briefly explain the game franchise
*Pokemon*, which is what my program works around. My hope is that, by the end 
of the explanation, the significance of my project will be more obvious.
If you are familiar with the Pokemon franchise, you do not have to read this part.

Pokemon are creatures that are defined by 1 or 2 (but never zero) of the 
existing 18 types. Some types are stronger, neutral, or weak against other types.
For instance, fire type is strong against grass type, neutral against electric types,
but is weak to water types.

Pokemon can also learn up to 4 moves (which are like the "spells" they can cast), 
which are also defined by a type. For instance, if a fire-type pokemon were struck by 
another pokemon with a water-type move, it would deal a relatively larger blow to 
its HP (health points) than when struck by an electric or grass type move.

This type mechanism plays a prevalent role in what are called "Pokemon battles".
A pokemon battle is (almost always) a 1-on-1 duel between two players.
Each player gets to bring up to 6 pokemon max (the player's **pokemon team**).
Each player sends out 1 pokemon at a time, taking turns casting their moves
on each other until one's HP depletes (ie. a pokemon "faints").

The type mechanism plays a large factor in determining the 
fate of a battle, since it will determine how much HP points to chunk with
every move casted, based on the battling pokemons' types. 
Once a pokemon has fainted, it would be switched out for 
another pokemon. This process is repeated until one player's whole team faints.

___

## Updates for the future

- I am fixing a rendering issue that pops up when the program is ran on another application that isn't IntelliJ.
- I am seeking to refactor the GUI portion of my program, so that the code is more readable and maintainable.
- I am looking to hook my program up to an API, where I can make requests to fetch various data about pokemon.

---  
  
## Credits

- This project was inspired by the Pokemon franchise. Pokemon belongs to the Pokemon Company; I am not associated with The Pokemon Company. **No copyright infringement was intended**. This project wasn't and will never be used for commercial gains.
- The pokemon type images are designed by falke2009 on DeviantArt.
    * https://www.deviantart.com/falke2009/art/Pokemon-Type-Symbols-Downloadable-403610684
- The pokemon portrait images are from veekun.
    * https://veekun.com/dex/downloads
