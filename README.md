# CaveBot
My first bot.

## To-Do
* Make !gameroom create the channel in a specific category.
* Make a command for a user to add a role to themselves, to change the colour of their name.
* Get some WTF Engine incorporated.

## Commands
### !ping
Location: /src/main/java/CaveDann/Main.java

On a call of "!ping" in a server, returns "pong!".

### !gameroom
Location: /src/main/java/CaveDann/Main.java

On a call of "!gameroom", creates a 10-user voice channel called "game room". If no user joins within 30 seconds, deletes said channel. Otherwise, waits for the last user to leave the channel and then deletes.

