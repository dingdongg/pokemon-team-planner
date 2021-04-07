# My CPSC210 Personal Project

## Pokemon Team Planner

***Project background explanation***

Before I delve into my project, I will briefly explain the game franchise
*Pokemon*, which is what my program works around. My hope is that, by the end 
of the explanation, the significance of my project will be more obvious.

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

***Project proposal***

The project I will be designing builds off of this battling idea. 
It is a program that allows users to build their
own collection of Pokemon teams. It will allow users to edit, create, and
store a collection of Pokemon team ideas. In its final stages, this program
will also take the pokemons' types into consideration and give feedback on the 
strengths and weaknesses of the team. Hence, it will be a useful tool for anyone 
looking to plan out Pokemon teams in their playthrough of Pokemon games, for both 
casual and competitive players alike.

This project is of interest to me because I loved playing pokemon games as a kid. 
Even to this day, I would spend most of my free time replaying these
same games because to me they are classics. My favorite part with each playthrough
is the part where I sit down and plan out a whole new pokemon team. This often took
up a long time because I always wanted my team to be as strong as it can be
without having too many weaknesses. And because I don't have much spare time in
university, I never ended up finishing my team and never got to replay the game.
By creating this program, I am hoping that my team planning process will become a lot
shorter, so that I will be able to actually play through the game with my pokemon team.

---

## User stories

- As a user, I want to be able to create a new pokemon having a name and 1 or 2 pokemon types
- As a user, I want to be able to design a pokemon team and add it to my collection of teams
- As a user, I want to be able to edit existing pokemon teams
- As a user, I want to be able to delete existing pokemon team(s)
- As a user, I want to be able to view the contents of a selected team, which will list
  the contained pokemon's names and their type(s)
  
##### User stories (Phase 2)
- As a user, I want to be able to save my collection of pokemon teams to file
- As a user, I want to be able to load my collection of pokemon teams from file

##### Phase 4: Task 2
- I chose to implement the option where I test and design a class in my model package
- The class I chose was PokemonTeamCollection, and the robust method is called `getTeam()`, 
which has the ability to throw `TeamNotFoundException`, a checked exception.
---

#### Functions I want to implement later in the project

- Giving feedback on what pokemon types a given team is offensively strong against,
  as well as the types the team is defensively weak to 
  
  
## Credits

- This project was inspired by the Pokemon franchise. Pokemon belongs to the Pokemon Company; I am not associated with The Pokemon Company. **No copyright infringement was intended**. This project wasn't and will never be used for commercial gains.
- The pokemon type images are designed by falke2009 on DeviantArt.
    * https://www.deviantart.com/falke2009/art/Pokemon-Type-Symbols-Downloadable-403610684
- The pokemon portrait images are from veekun.
    * https://veekun.com/dex/downloads
