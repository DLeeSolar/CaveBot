package Bot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.ServerVoiceChannelBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Please provide a valid token as the first argument!");
            return;
        }

        // The token is the first argument of the program
        String token = args[0];

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
                if (event.getServer().isPresent()) {
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
                } else {
                    event.getChannel().sendMessage("Server not found!");
                }
            }
        });

        // Add a listener which changes a user's role, if someone writes "!colour
        api.addMessageCreateListener(event -> {
            if (event.getMessage().getContent().equalsIgnoreCase("!colour")) {
                Server server = event.getServer().get();
                User user = event.getMessageAuthor().asUser().get();
                Role role = server.getRolesByNameIgnoreCase("DS").get(0);
                server.addRoleToUser(user, role);
            }
        });

        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());

    }

}
