package Bot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.ServerVoiceChannelBuilder;
import org.javacord.api.entity.server.Server;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Please provide a valid token as the first argument!");
            return;
        }

        // The token is the first argument of the program
        // You can add an argument via Run > Edit Configurations > Main > Program Arguments
        String token = args[0];

        // Insert your bot's token here
        // String token = "NzI3Nzg1MjgzODczMTQ0ODk1.Xvyu0w.NyyyMWyP3QSbweP9_-FtdKxVuMY";

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        // Add a listener which answers with "Pong!" if someone writes "!ping"
        api.addMessageCreateListener(event -> {
            if (event.getMessage().getContent().equalsIgnoreCase("!ping")) {
                event.getChannel().sendMessage("Pong!");
            }
        });

        // Add a listener which creates a channel in the Game Rooms category, prepended with the user's name, if someone writes "!gameroom"
        api.addMessageCreateListener(event -> {
            if (event.getMessage().getContent().equalsIgnoreCase("!gameroom")) {
                Server server = event.getServer().get();
                ChannelCategory ccat = server.getChannelCategoriesByName("Game Rooms").get(0);
                String uname = event.getMessageAuthor().getDisplayName();
                ServerVoiceChannel channel = new ServerVoiceChannelBuilder(server)
                        .setCategory(ccat)
                        .setName(uname + "'s Room")
                        .setUserlimit(10)
                        .create()
                        .join();

                // Delete the channel if the last user leaves
                channel.addServerVoiceChannelMemberLeaveListener(event2 -> {
                    if (event2.getChannel().getConnectedUserIds().isEmpty()) {
                        event2.getChannel().delete();
                    }
                });

                // Delete the channel if no user joined in the first 30 seconds
                api.getThreadPool().getScheduler().schedule(() -> {
                    if (channel.getConnectedUserIds().isEmpty()) {
                        channel.delete();
                    }
                }, 30, TimeUnit.SECONDS);
            }
        });

        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());

    }

}
