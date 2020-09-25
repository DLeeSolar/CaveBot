package Bot.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.awt.*;
import java.io.File;

public class EventEmbed implements MessageCreateListener {

    /*
     * This command is used to have the bot say "Pong!" in response to a message of "Ping!".
     */

    @Override
    public void onMessageCreate(MessageCreateEvent event) {

        if (event.getMessage().getContent().equalsIgnoreCase("?event")) {
            String euname = event.getMessageAuthor().getDisplayName();

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Game Night")
                    .setDescription("Let's play some games together!")
                    .setAuthor(euname)
                    .addField("AmongUs", "Dirt cheap. On Steam. It'd be Sus not to.")
                    .addInlineField("Crewmates", "None")
                    .addInlineField("Impostors", "SIMON")
                    .setColor(Color.getHSBColor(151,140,156))
                    .setFooter("React to join.")
                    .setImage(new File("C:/Users/Danny/Pictures/BCb.jpg"))
                    .setThumbnail(new File("F:/Firefox_Downloads/token_1(13).png"));

            TextChannel channel = event.getChannel();
            channel.sendMessage(embed);

        }

    }

}
