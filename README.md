# Super Hero Chess

A board game inspired by chess, with super hero characters that have different superpower abilities.
<br/>
<br/>
<img src = "images/Game preview.PNG">

## Game Rules

### Moves

| Pieces               | Moves Allowed                                                                                                                    |
|----------------------|----------------------------------------------------------------------------------------------------------------------------------|
| Super                | :arrow_left: :arrow_up: :arrow_right: :arrow_down:                                                                               |
| Ranged               | :arrow_left: :arrow_up: :arrow_right: :arrow_down: :arrow_upper_left: :arrow_upper_right: :arrow_lower_left: :arrow_lower_right: |
| Medic                | :arrow_left: :arrow_up: :arrow_right: :arrow_down:                                                                               |
| Armored              | :arrow_left: :arrow_up: :arrow_right: :arrow_down: :arrow_upper_left: :arrow_upper_right: :arrow_lower_left: :arrow_lower_right: |
| Speedster            | :arrow_left: :arrow_up: :arrow_right: :arrow_down: :arrow_upper_left: :arrow_upper_right: :arrow_lower_left: :arrow_lower_right: |
| Tech                 | :arrow_upper_left: :arrow_upper_right: :arrow_lower_left: :arrow_lower_right:                                                    |
| Sidekick  (Player 1) | :arrow_down: :arrow_up: :arrow_right: :arrow_upper_right: :arrow_lower_right:                                                    |
| Sidekick  (Player 2) | :arrow_left: :arrow_down: :arrow_up:  :arrow_upper_left: :arrow_lower_left:                                                      |

### Abilities

| Pieces     | Special Ability                                                                                                                                                                                                                                                                                                                                                                                                     |
|------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Super      | Smashes two adjacent cells in the specified direction, eliminating any enemy pieces within them.                                                                                                                                                                                                                                                                                                                    |
| Ranged     | Eliminates the first enemy piece that is encountered in the specified direction. The distance of the enemy piece makes no difference, provided that the path is not blocked by any friendly piece.                                                                                                                                                                                                                  |
| Medic      | Resurrects an eliminated friendly piece to the adjacent cell in the chosen direction. The special ability of the resurrected piece is also restored.                                                                                                                                                                                                                                                                |
| Armored    | Survives the first damage taken through any type of attack.                                                                                                                                                                                                                                                                                                                                                         |
| Speedster  | Traverses two cells instead of one in any movement. This ability is a passive one i.e. it is always activated until the Speedster is eliminated.                                                                                                                                                                                                                                                                    |
| Tech       | Can use one of the following, without ending the turn afterwards: 1. Teleporting a friendly piece into any empty cell on the board. 2. Hacking an enemy hero piece so that it cannot use its ability. This is applicable to non-passive powers only. 3. Restoring the ability of another friendly piece, enabling it to be used again. This can revert the effect of the Tech’s second ability, namely the hacking. |
| Sidekick   | Whenever a sidekick piece eliminates a hero piece, this sidekick piece is replaced with a new hero piece of the same type of the eliminated hero piece. This ability is a passive one                                                                                                                                                                                                                               |

### Winning Condition

When the payload has been pumped up six times.

### Payload Movement

The payload moves one step after:
1. Eliminating an enemy super hero piece.
2. Eliminating two enemy sidekick pieces.


Watch this [video](https://drive.google.com/file/d/1ZIOyuHWoi4PrRoK3NXoAyk4YN463KgiK/view?usp=sharing) for a full gameplay demo.

