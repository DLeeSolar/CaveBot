# CaveBot
My first bot.

## To-Do
* Get commands out of Main.
* Make a command to delete old messages.
* Make a command for formatting events. 
* Setup a channel on the server for talking to CaveBot. 
* Get some WTF Engine incorporated.

## Commands
### ?ping
Location: /src/main/java/CaveDann/Main.java

On a call of "?ping" in a server, returns "pong!".

### ?gameroom
Location: /src/main/java/CaveDann/Main.java

On a call of "?gameroom", creates a 10-user voice channel called "game room". If no user joins within 30 seconds, deletes said channel. Otherwise, waits for the last user to leave the channel and then deletes.

### ?colour [argument]
Location: /src/main/java/CaveDann/Main.java

Calling "?colour [argument]" changes the colour of the users name. 
Has a help message if you get it wrong. 

## Done
* Make basic pingpong command.
* Make ?gameroom create the channel in a specific category.
* Make a command for a user to add a role to themselves, to change the colour of their name.
