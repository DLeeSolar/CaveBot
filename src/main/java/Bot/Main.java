package Bot;

import Bot.commands.PingPong;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.ServerVoiceChannelBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.Arrays;
import java.util.List;
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
        //api.addMessageCreateListener(event -> {
        //    if (event.getMessage().getContent().equalsIgnoreCase("Ping!")) {
        //        event.getChannel().sendMessage("Pong!");
        //    }
        //});
        api.addMessageCreateListener(new PingPong());

        // Add a listener which creates a channel in the Game Rooms category, prepended with the user's name, if someone writes "!gameroom"
        api.addMessageCreateListener(event -> {
            if (event.getMessage().getContent().equalsIgnoreCase("?gameroom")) {
                if (event.getServer().isPresent()) {
                    Server server = event.getServer().get();
                    ChannelCategory ccat = server.getChannelCategoriesByName("Lobby").get(0);
                    String uname = event.getMessageAuthor().getDisplayName();
                    ServerVoiceChannel channel = new ServerVoiceChannelBuilder(server)
                            .setCategory(ccat)
                            .setName(uname + "'s Room")
                            .setUserlimit(10)
                            .create()
                            .join();
                    event.deleteMessage();

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
            String content = event.getMessage().getContent();
            //if (event.getMessage().getContent().equalsIgnoreCase("!colour")) {
            //if (event.getMessage().getContent().contains("?colour")) {
            if (content.startsWith("?colour ")) {
                // You're trying to do some stuff with lists here so that you can match parts to the list,
                // to figure out which role (if any) need to be removed, and then add the list in the command.
                // Ideally, the final form of this command would be "!colour <colour-code>", which removes any
                // previous colour, and adds the desired one.
                //
                //String[] rmessage = event.getMessage().getContent().split("\\s+");
                String[] rmessage = content.split("\\s+");
                String[] rcolours = {"B", "DS", "S", "DO", "O", "DY", "Y", "DPi", "Pi", "DPu", "Pu", "DB", "Bl", "TB", "DG", "G", "DT", "V", "T"};
                List<String> rclist = Arrays.asList(rcolours);

                if (rclist.contains(rmessage[1])) {

                    Server server = event.getServer().get();
                    User user = event.getMessageAuthor().asUser().get();
                    String uname = event.getMessageAuthor().getDisplayName();

                    for (String i : rcolours) {
                        Role rolei = server.getRolesByNameIgnoreCase(i).get(0);
                        server.removeRoleFromUser(user, rolei);
                    }

                    Role newrole = server.getRolesByNameIgnoreCase(rmessage[1]).get(0);
                    server.addRoleToUser(user, newrole);
                    //Role roleB = server.getRolesByNameIgnoreCase("B").get(0);
                    //server.removeRoleFromUser(user, roleB);

                    event.deleteMessage();
                    event.getChannel().sendMessage(uname + "'s Roles Changed");
                } else {
                    event.getChannel().sendMessage(rmessage[1] + " does not exist");
                    event.getChannel().sendMessage("Colour roles are B (Black), DS (Dark Salmon), S (Salmon), DO (Dark Orange), O (Orange), DY (Dark Yellow), Y (Yellow), DPi (Dark Pink), Pi (Pink), DPu (Dark Purple), DB (Dark Blue), Bl (Blue), TB (TurtleEgg Blue, DG (Dark Green), G (Green), DT (Dark Turquoise), T (Turquoise), V (Viridian)");
                }
            }
        });

        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());

    }

}
