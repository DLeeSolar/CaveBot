package Bot;

import Bot.commands.Colour;
import Bot.commands.EventEmbed;
import Bot.commands.Gameroom;
import Bot.commands.PingPong;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Please provide a valid token as the first argument!");
            return;
        }

        // The token is the first argument of the program
        String token = args[0];

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        // Add listeners for commands
        api.addMessageCreateListener(new PingPong());
        api.addMessageCreateListener(new Gameroom());
        api.addMessageCreateListener(new Colour());
        api.addMessageCreateListener(new EventEmbed());

        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());

    }

}
