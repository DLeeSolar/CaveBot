package Bot.commands;

import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.ServerVoiceChannelBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.concurrent.TimeUnit;

public class Gameroom implements MessageCreateListener {

    /*
     * This command is used to create Gameroom Voice Channels.
     * Any user can use this command.
     */

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

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
                event.getApi().getThreadPool().getScheduler().schedule(() -> {
                    if (channel.getConnectedUserIds().isEmpty()) {
                        channel.delete();
                    }
                }, 30, TimeUnit.SECONDS);
            } else {
                event.getChannel().sendMessage("Server not found!");
            }
        }

    }

}
